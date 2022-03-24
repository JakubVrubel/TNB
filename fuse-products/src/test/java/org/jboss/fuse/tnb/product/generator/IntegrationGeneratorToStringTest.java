package org.jboss.fuse.tnb.product.generator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.jboss.fuse.tnb.common.product.ProductType;
import org.jboss.fuse.tnb.product.ck.integration.builder.CamelKIntegrationBuilder;
import org.jboss.fuse.tnb.product.integration.builder.AbstractIntegrationBuilder;
import org.jboss.fuse.tnb.product.integration.builder.IntegrationBuilder;
import org.jboss.fuse.tnb.product.integration.generator.IntegrationGenerator;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("unit")
public class IntegrationGeneratorToStringTest extends AbstractIntegrationGeneratorTest {
    @Override
    public String process(AbstractIntegrationBuilder<?> ib) {
        return IntegrationGenerator.toString(ib);
    }

    @Override
    @Test
    public void shouldProcessRouteBuilderTest() {
        setProduct(ProductType.CAMEL_SPRINGBOOT);

        IntegrationBuilder ib = dummyIb();
        assertThat(process(ib)).isEqualTo(ib.getRouteBuilder().get().toString());
    }

    @Test
    public void shouldProcessAdditionalClassesTest() {
        setProduct(ProductType.CAMEL_SPRINGBOOT);

        IntegrationBuilder ib = builderWithAdditionalClass();
        String source = process(ib);

        String expected = ib.getAdditionalClasses().get(0).toString();
        expected = expected.substring(expected.indexOf("public"), expected.length() - 1).trim();
        expected = expected.replace("public class", "public static class");
        assertThat(source.replaceAll(" ", "")).contains(expected.replaceAll(" ", ""));
        assertThat(source).contains("import com.example.Whatever");
    }

    @Test
    public void shouldProcessStringSourceTest() {
        setProduct(ProductType.CAMEL_K);
        final String integrationSource = "this is the integration content";
        CamelKIntegrationBuilder ib = new CamelKIntegrationBuilder("test").fromString(integrationSource);
        String source = process(ib);

        assertThat(source).isEqualTo(integrationSource);
    }

    @Test
    public void shouldFailForCamelKWithNoSourceTest() {
        setProduct(ProductType.CAMEL_K);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> process(new CamelKIntegrationBuilder("")));
    }

    @Test
    public void shouldFailForNoRouteBuilderOrSourceTest() {
        setProduct(ProductType.CAMEL_QUARKUS);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> process(new IntegrationBuilder("")));
    }
}

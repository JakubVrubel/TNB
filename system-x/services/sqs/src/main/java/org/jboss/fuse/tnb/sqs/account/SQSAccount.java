package org.jboss.fuse.tnb.sqs.account;

import org.jboss.fuse.tnb.aws.account.AWSAccount;

public class SQSAccount extends AWSAccount {
    private String queueUrlPrefix;
    private String queueArnPrefix;

    @Override
    public String credentialsId() {
        return "aws";
    }

    public void setQueueUrlPrefix(String queueUrlPrefix) {
        this.queueUrlPrefix = queueUrlPrefix;
    }

    public void setQueueArnPrefix(String queueArnPrefix) {
        this.queueArnPrefix = queueArnPrefix;
    }

    public String queueUrlPrefix() {
        return queueUrlPrefix;
    }

    public String queueArnPrefix() {
        return queueArnPrefix;
    }
}

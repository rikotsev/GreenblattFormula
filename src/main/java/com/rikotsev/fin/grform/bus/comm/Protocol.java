package com.rikotsev.fin.grform.bus.comm;

import org.springframework.stereotype.Service;

/**
 * Contains all the generic communication information and internal for the application statuses.
 */
@Service
public class Protocol {

    public enum ActionStatusType {
        STARTED,
        ERROR,
        COMPLETED;
    }

    /**
     * The status of an action executed against the API
     * that doesn't get
     */
    public class ActionStatus {

        private ActionStatus() {}

        private ActionStatusType type;
        private String message;
        /**
         * -1 - error
         * 1 - warnings
         * 0 - flawless
         */
        private int statusCode;
        private long entityId;

        public ActionStatusType getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public long getEntityId() {
            return entityId;
        }
    }

    public class ActionStatusBuilder {

        private ActionStatusType type;
        private String message;
        private int statusCode;
        private long entityId;

        private ActionStatusBuilder() {}

        public ActionStatusBuilder(final ActionStatusType type) {
            this.type = type;
        }

        public ActionStatusBuilder message(final String message) {
            this.message = message;

            return this;
        }

        public ActionStatusBuilder statusCode(final int statusCode) {
            this.statusCode = statusCode;

            return this;
        }

        public ActionStatusBuilder entityId(final long entityId) {
            this.entityId = entityId;

            return this;
        }

        public ActionStatus build() {
            final ActionStatus as = new ActionStatus();
            as.type = this.type;
            as.message = this.message;
            as.statusCode = this.statusCode;
            as.entityId = this.entityId;

            return as;
        }

    }

    public ActionStatusBuilder actionStatusBuilder(final ActionStatusType type) {
        return new ActionStatusBuilder(type);
    }

}

package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.io.Serializable;

public class EventMessage implements Serializable {
    private EventHeader eventHeader;
    private String eventBody;

    public EventMessage() {
    }

    @JsonIgnore
    public static Builder builder() {
        return new Builder();
    }

    private EventMessage(Builder builder) {
        setEventHeader(builder.eventHeader);
        setEventBody(builder.eventBody);
    }

    public void setEventHeader(EventHeader eventHeader) {
        this.eventHeader = eventHeader;
    }

    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }

    public EventHeader getEventHeader() {
        return eventHeader;
    }

    public String getEventBody() {
        return eventBody;
    }

    @JsonIgnoreType
    public static final class Builder implements Serializable {
        private EventHeader eventHeader;
        private String eventBody;

        //needed for serializer
        private Builder() {
        }

        public Builder eventHeader(EventHeader val) {
            eventHeader = val;
            return this;
        }

        public Builder eventBody(String val) {
            eventBody = val;
            return this;
        }

        public EventMessage build() {
            return new EventMessage(this);
        }
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "eventHeader=" + eventHeader +
                ", eventBody='" + eventBody + '\'' +
                '}';
    }
} 
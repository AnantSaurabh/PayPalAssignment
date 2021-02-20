package com.paypal.bfs.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import org.jsonschema2pojo.AbstractAnnotator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class CustomAnnotator extends AbstractAnnotator {

    @Override
    public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
        super.propertyInclusion(clazz, schema);
        JsonNode additionalProperties = schema.get("additionalProperties");
        if(additionalProperties.has("entity")) {
            clazz.annotate(Entity.class);
        }
    }

    @Override
    public void propertyField(JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
        if (propertyNode.has("identifier")) {
            field.annotate(Id.class);
        }
        if (propertyNode.has("required")) {
            field.annotate(NotNull.class);
        }
        if (propertyNode.has("validate")) {
            field.annotate(Valid.class);
        }
        if (propertyNode.has("joinColumnPrimary")) {
            field.annotate(OneToOne.class).param("cascade", CascadeType.ALL);
            field.annotate(JoinColumn.class)
                    .param("name",propertyNode.get("name").textValue())
                    .param("referencedColumnName", propertyNode.get("referencedColumnName").textValue());
        }
        if (propertyNode.has("joinColumnSecondary")) {
            field.annotate(OneToOne.class).param("mappedBy",propertyNode.get("mappedBy").textValue());
        }
    }
}


package org.slasoi.gslam.syntaxconverter;

import org.slasoi.slamodel.sla.SLATemplate;
/**
 * 
 * @author Peter A. Chronz
 * 
 *         SLASOITemplateRenderer is an implementation for the XML-representation of the SLA model. It uses the XmlElementRenderer
 *         for rendering the individual elements.
 */
public abstract class SLATemplateRenderer {
    protected SLATemplate slaModel;
    protected Object product;
    protected ElementRenderer elementRenderer;

    /**
     * Renders an SLATemplate object to a string. 
     * @param slaModel The template as SLA model object.
     * @return String representation of the respective SLA template.
     * @throws Exception
     */
    public String renderSLATemplate(SLATemplate slaModel) throws Exception {
        this.slaModel = slaModel;

        initTargetModel();
        addParties();
        addInterfaces();
        addVariableDeclarations();
        addAgreementTerms();

        return serializeProduct();
    }

    protected abstract void initTargetModel() throws Exception;

    protected abstract void addParties() throws Exception;

    protected abstract void addInterfaces() throws Exception;

    protected abstract void addVariableDeclarations() throws Exception;

    protected abstract void addAgreementTerms() throws Exception;

    protected abstract boolean validateSLARendering() throws Exception;

    protected abstract String serializeProduct() throws Exception;
}

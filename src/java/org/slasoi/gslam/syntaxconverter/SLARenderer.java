package org.slasoi.gslam.syntaxconverter;

import org.slasoi.slamodel.sla.SLA;

/**
 * 
 * @author Peter A. Chronz
 *
 * This abstract class provides template methods for rendering an SLA.
 */
public abstract class SLARenderer {

    protected ElementRenderer elementRenderer;
    protected SLA slaModel;
    protected Object product;

    /**
     * Renders an SLA object to a specific string-based representation.
     * @param slaModel
     * @return
     * @throws Exception
     */
    public String renderSLA(SLA slaModel) throws Exception {
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

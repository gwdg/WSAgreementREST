package org.slasoi.gslam.syntaxconverter;

import org.slasoi.slamodel.sla.SLA;

/**
 * 
 * @author Peter A. Chronz
 * 
 *         This abstract class provides template methods for parsing an SLA document.
 */
public abstract class SLAParser {
    protected SLA slaModel;
    protected ElementParser elementParser;

    /**
     * Parses an SLA from a serialized SLA string.
     * @param slaObject The serializec SLA.
     * @return Parsed SLA object.
     * @throws Exception
     */
    public SLA parseSLA(String slaObject) throws Exception {

        initSLAModel(slaObject);
        addParties();
        addInterfaces();
        addVariableDeclarations();
        addAgreementTerms();

        if (!validateSLAModel())
            throw new Exception("Parsed SLA model is invalid.");

        return this.slaModel;
    }

    protected abstract boolean validateSLAModel();

    protected abstract void addAgreementTerms() throws Exception;

    protected abstract void addVariableDeclarations() throws Exception;

    protected abstract void addInterfaces() throws Exception;

    protected abstract void addParties() throws Exception;

    protected abstract void initSLAModel(String slaTemplateRendering) throws Exception;
}

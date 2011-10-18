package org.slasoi.gslam.syntaxconverter;

import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.VariableDeclr;

import eu.slaatsoi.slamodel.AgreementPartyType;
import eu.slaatsoi.slamodel.AgreementTermType;
import eu.slaatsoi.slamodel.InterfaceDeclrType;
import eu.slaatsoi.slamodel.SLATemplateDocument;
import eu.slaatsoi.slamodel.VariableDeclrType;
import eu.slaatsoi.slamodel.SLATemplateDocument.SLATemplate;

/**
 * 
 * @author Peter A. Chronz
 * 
 *         SLASOIRenderer is an implementation for the XML-representation of the SLA model providing template rendering capabilities. It uses the XmlElementRenderer
 *         for rendering the individual elements.
 */
public class SLASOITemplateRenderer extends SLATemplateRenderer {

    protected SLATemplateDocument targetTemplate;

    public SLASOITemplateRenderer() {
        super();
        this.elementRenderer = new XmlElementRenderer();
    }

    protected void addAgreementTerms() throws Exception {
        SLATemplate slaTemplateXml = this.targetTemplate.getSLATemplate();

        // AgreementTerm
        AgreementTerm[] agreementTerms = this.slaModel.getAgreementTerms();
        AgreementTermType[] agreementTermsXml = new AgreementTermType[agreementTerms.length];
        for (int i = 0; i < agreementTermsXml.length; i++) {
            AgreementTerm agreementTerm = agreementTerms[i];
            agreementTermsXml[i] = (AgreementTermType) this.elementRenderer.renderAgreementTerm(agreementTerm);
        }
        slaTemplateXml.setAgreementTermArray(agreementTermsXml);
    }

    protected void addInterfaces() throws Exception {
        SLATemplate slaTemplateXml = this.targetTemplate.getSLATemplate();

        // InterfaceDeclr
        InterfaceDeclr[] interfaceDeclrs = this.slaModel.getInterfaceDeclrs();
        InterfaceDeclrType[] interfaceDeclrsXml = new InterfaceDeclrType[interfaceDeclrs.length];
        for (int i = 0; i < interfaceDeclrsXml.length; i++) {
            InterfaceDeclr interfaceDeclr = interfaceDeclrs[i];
            interfaceDeclrsXml[i] = (InterfaceDeclrType) this.elementRenderer.renderInterfaceDeclr(interfaceDeclr);
        }
        slaTemplateXml.setInterfaceDeclrArray(interfaceDeclrsXml);
    }

    protected void addParties() throws Exception {
        SLATemplate slaTemplateXml = this.targetTemplate.getSLATemplate();

        // Party
        Party[] parties = this.slaModel.getParties();
        AgreementPartyType[] partiesXml = new AgreementPartyType[parties.length];
        for (int i = 0; i < parties.length; i++) {
            Party party = parties[i];
            partiesXml[i] = (AgreementPartyType) this.elementRenderer.renderParty(party);
        }
        slaTemplateXml.setPartyArray(partiesXml);
    }

    protected void addVariableDeclarations() throws Exception {
        SLATemplate slaTemplateXml = this.targetTemplate.getSLATemplate();

        // VariableDeclr
        VariableDeclr[] variableDeclrs = this.slaModel.getVariableDeclrs();
        if (variableDeclrs != null) {
            VariableDeclrType[] variableDeclrsXml = new VariableDeclrType[variableDeclrs.length];
            for (int i = 0; i < variableDeclrsXml.length; i++) {
                VariableDeclr variableDeclr = variableDeclrs[i];
                variableDeclrsXml[i] = (VariableDeclrType) this.elementRenderer.renderVariableDeclr(variableDeclr);
            }
            slaTemplateXml.setVariableDeclrArray(variableDeclrsXml);
        }
    }

    protected void initTargetModel() throws Exception {
        this.targetTemplate = SLATemplateDocument.Factory.newInstance();
        this.product = this.targetTemplate;

        SLATemplate slaTemplateXml = this.targetTemplate.addNewSLATemplate();

        // UUID
        assert this.slaModel.getUuid() != null : "The template to render does not contain an UUID!";
        UUID uuid = this.slaModel.getUuid();
        slaTemplateXml.setUUID(uuid.getValue());

        // ModelVersion
        assert this.slaModel.getModelVersion() != null : "The template to render does not contain a ModelVersion!";
        String modelVersion = slaModel.getModelVersion();
        slaTemplateXml.setModelVersion(modelVersion);

        // Annotation
        elementRenderer.setAnnotation(this.slaModel, slaTemplateXml);

        // Party
        addParties();

        // InterfaceDeclr
        addInterfaces();

        // VariableDeclr
        addVariableDeclarations();

        // AgreementTerm
        addAgreementTerms();
    }

    protected boolean validateSLARendering() {
        return this.targetTemplate.validate();
    }

    protected String serializeProduct() throws Exception {
        return this.targetTemplate.xmlText();
    }

}

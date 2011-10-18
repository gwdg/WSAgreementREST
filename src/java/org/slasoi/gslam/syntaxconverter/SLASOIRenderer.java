package org.slasoi.gslam.syntaxconverter;

import java.util.Calendar;

import org.apache.xmlbeans.XmlCalendar;
import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.VariableDeclr;

import eu.slaatsoi.slamodel.AgreementPartyType;
import eu.slaatsoi.slamodel.AgreementTermType;
import eu.slaatsoi.slamodel.InterfaceDeclrType;
import eu.slaatsoi.slamodel.SLADocument;
import eu.slaatsoi.slamodel.VariableDeclrType;
import eu.slaatsoi.slamodel.SLADocument.SLA;

/**
 * 
 * @author Peter A. Chronz
 * 
 *         SLASOIRenderer is an implementation for the XML-representation of the SLA model. It uses the XmlElementRenderer
 *         for rendering the individual elements.
 */
public class SLASOIRenderer extends SLARenderer {

    protected SLADocument targetSLA;

    /**
     * Creates a new renderer with XmlElementRenderer as internal renderer.
     */
    public SLASOIRenderer() {
        this.elementRenderer = new XmlElementRenderer();
    }

    protected void addAgreementTerms() throws Exception {
        SLA slaTemplateXml = this.targetSLA.getSLA();

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
        SLA slaTemplateXml = this.targetSLA.getSLA();

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
        SLA slaTemplateXml = this.targetSLA.getSLA();

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
        SLA slaTemplateXml = this.targetSLA.getSLA();

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
        this.targetSLA = SLADocument.Factory.newInstance();
        this.product = this.targetSLA;

        SLA slaXml = this.targetSLA.addNewSLA();

        // UUID
        assert this.slaModel.getUuid() != null : "The SLA to render does not contain an UUID!";
        UUID uuid = this.slaModel.getUuid();
        slaXml.setUUID(uuid.getValue());

        // ModelVersion
        assert this.slaModel.getModelVersion() != null : "The SLA to render does not contain a ModelVersion!";
        String modelVersion = this.slaModel.getModelVersion();
        slaXml.setModelVersion(modelVersion);

        // AgreedAt
        assert this.slaModel.getAgreedAt() != null : "The SLA to render does not contain an AgreedAt!";
        Calendar agreedAt = this.slaModel.getAgreedAt().getValue();
        XmlCalendar agreedAtCal = new XmlCalendar(agreedAt.getTime());
        this.targetSLA.getSLA().setAgreedAt(agreedAtCal);

        // EffectiveFrom
        assert this.slaModel.getEffectiveFrom() != null : "The SLA to render does not contain an EffectiveFrom!";
        Calendar effectiveFrom = this.slaModel.getEffectiveFrom().getValue();
        XmlCalendar effectiveFromCal = new XmlCalendar(effectiveFrom.getTime());
        this.targetSLA.getSLA().setEffectiveFrom(effectiveFromCal);

        // EffectiveUntil
        assert this.slaModel.getEffectiveUntil() != null : "The SLA to render does not contain an EffectiveUntil!";
        Calendar effectiveUntil = this.slaModel.getEffectiveUntil().getValue();
        XmlCalendar effectiveUntilCal = new XmlCalendar(effectiveUntil.getTime());
        this.targetSLA.getSLA().setEffectiveUntil(effectiveUntilCal);

        // TemplateId
        assert this.slaModel.getTemplateId() != null : "The SLA to render does not contain a TemplateId!";
        UUID templateId = this.slaModel.getTemplateId();
        this.targetSLA.getSLA().setTemplateId(templateId.getValue());

        // Annotation
        elementRenderer.setAnnotation(this.slaModel, slaXml);

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
        // TODO Auto-generated method stub
        return false;
    }

    protected String serializeProduct() throws Exception {
        return this.targetSLA.xmlText();
    }

}

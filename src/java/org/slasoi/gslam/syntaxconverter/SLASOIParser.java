package org.slasoi.gslam.syntaxconverter;

import java.util.Calendar;

import org.apache.xmlbeans.XmlCalendar;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.primitives.TIME;
import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.SLA;
import org.slasoi.slamodel.sla.VariableDeclr;

import eu.slaatsoi.slamodel.AgreementPartyType;
import eu.slaatsoi.slamodel.AgreementTermType;
import eu.slaatsoi.slamodel.InterfaceDeclrType;
import eu.slaatsoi.slamodel.SLADocument;
import eu.slaatsoi.slamodel.VariableDeclrType;

/**
 * 
 * @author Peter A. Chronz
 * 
 *         SLASOIParser is an implementation for the XML-representation of the SLA model. It uses the XmlElementParser
 *         for parsing the individual elements.
 */
public class SLASOIParser extends SLAParser {

    protected SLADocument renderedSLA;

    /**
     * Creates a new parser with the XmlElementParser as internal component.
     */
    public SLASOIParser() {
        this.elementParser = new XmlElementParser();
    }
    
    protected void initSLAModel(String slaTemplateRendering) throws Exception {
        assert slaTemplateRendering != null : "SLA template rendering to be parsed is null!";
        this.renderedSLA = SLADocument.Factory.parse(slaTemplateRendering);
        this.slaModel = new SLA();

        // Annotation
        this.elementParser.setAnnotation(slaModel, this.renderedSLA.getSLA());

        // UUID
        assert this.renderedSLA.getSLA().getUUID() != null : "The SLA to render does not have an UUID!";
        String uuid = this.renderedSLA.getSLA().getUUID();
        this.slaModel.setUuid(new STND(uuid));

        // ModelVersion
        // String modelVersion = this.renderedSLATemplate.getSLATemplate().getModelVersion();
        // TODO maybe provide some output if modelVersions do not fit

        // AgreedAt
        assert this.renderedSLA.getSLA().getAgreedAt() != null : "The SLA to render does not have an AgreedAt!";
        Calendar agreedAt = this.renderedSLA.getSLA().getAgreedAt();
        XmlCalendar agreedAtCal = new XmlCalendar();
        agreedAtCal.setTime(agreedAt.getTime());
        this.slaModel.setAgreedAt(new TIME(agreedAt));

        // EffectiveFrom
        assert this.renderedSLA.getSLA().getEffectiveFrom() != null : "The SLA to render does not have an EffectiveFrom!";
        Calendar effectiveFrom = this.renderedSLA.getSLA().getEffectiveFrom();
        XmlCalendar effectiveFromCal = new XmlCalendar(effectiveFrom.getTime());
        effectiveFrom.setTime(effectiveFrom.getTime());
        this.slaModel.setEffectiveFrom(new TIME(effectiveFromCal));

        // EffectiveUntil
        assert this.renderedSLA.getSLA().getEffectiveUntil() != null : "The SLA to render does not have an EffectiveUntil!";
        Calendar effectiveUntil = this.renderedSLA.getSLA().getEffectiveUntil();
        XmlCalendar effectiveUntilCal = new XmlCalendar();
        effectiveUntilCal.setTime(effectiveUntil.getTime());
        this.slaModel.setEffectiveUntil(new TIME(effectiveUntilCal));

        // TemplateId
        assert this.renderedSLA.getSLA().getTemplateId() != null : "The SLA to render does not have an ID!";
        String templateId = this.renderedSLA.getSLA().getTemplateId();
        this.slaModel.setTemplateId(new UUID(templateId));

        // Party
        addParties();

        // InterfaceDeclr
        addInterfaces();

        // VairableDeclr
        addVariableDeclarations();

        // AgreementTerm
        addAgreementTerms();
    }

    protected boolean validateSLAModel() {
        return true;
    }

    protected void addAgreementTerms() throws Exception {
        AgreementTermType[] agreementTermsXml = this.renderedSLA.getSLA().getAgreementTermArray();
        AgreementTerm[] agreementTerms = new AgreementTerm[agreementTermsXml.length];
        for (int i = 0; i < agreementTerms.length; i++) {
            AgreementTermType agreementTermXml = agreementTermsXml[i];
            agreementTerms[i] = this.elementParser.parseAgreementTerm(agreementTermXml);
        }
        this.slaModel.setAgreementTerms(agreementTerms);
    }

    protected void addInterfaces() throws Exception {
        InterfaceDeclrType[] interfacesXml = this.renderedSLA.getSLA().getInterfaceDeclrArray();
        InterfaceDeclr[] interfaces = new InterfaceDeclr[interfacesXml.length];
        for (int i = 0; i < interfaces.length; i++) {
            InterfaceDeclrType interfaceXml = interfacesXml[i];
            interfaces[i] = this.elementParser.parseInterfaceDeclraration(interfaceXml);
        }
        this.slaModel.setInterfaceDeclrs(interfaces);
    }

    protected void addParties() throws Exception {
        AgreementPartyType[] partiesXml = this.renderedSLA.getSLA().getPartyArray();
        Party[] parties = new Party[partiesXml.length];
        for (int i = 0; i < parties.length; i++) {
            AgreementPartyType agreementPartyXml = partiesXml[i];
            parties[i] = this.elementParser.parseAgreementParty(agreementPartyXml);
        }
        this.slaModel.setParties(parties);
    }

    protected void addVariableDeclarations() throws Exception {
        VariableDeclrType[] varDeclrsXml = this.renderedSLA.getSLA().getVariableDeclrArray();
        VariableDeclr[] varDeclrs = new VariableDeclr[varDeclrsXml.length];
        for (int i = 0; i < varDeclrs.length; i++) {
            VariableDeclrType varDeclrXml = varDeclrsXml[i];
            varDeclrs[i] = this.elementParser.parseVariableDeclr(varDeclrXml);
        }
        this.slaModel.setVariableDeclrs(varDeclrs);
    }

}

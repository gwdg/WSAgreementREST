package org.slasoi.gslam.syntaxconverter;

import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.SLATemplate;
import org.slasoi.slamodel.sla.VariableDeclr;

import eu.slaatsoi.slamodel.AgreementPartyType;
import eu.slaatsoi.slamodel.AgreementTermType;
import eu.slaatsoi.slamodel.InterfaceDeclrType;
import eu.slaatsoi.slamodel.SLATemplateDocument;
import eu.slaatsoi.slamodel.VariableDeclrType;

/**
 * 
 * @author Peter A. Chronz
 * 
 *         SLASOITemplateParser is an implementation for the XML-representation of the SLA model. It provides the capability for parsing SLA templates. It uses the XmlElementParser
 *         for parsing the individual elements.
 */
public class SLASOITemplateParser extends SLATemplateParser {
    protected SLATemplateDocument renderedSLATemplate;

    public SLASOITemplateParser() {
        super();
        this.elementParser = new XmlElementParser();
    }

    protected void addAgreementTerms() throws Exception {
        AgreementTermType[] agreementTermsXml = this.renderedSLATemplate.getSLATemplate().getAgreementTermArray();
        AgreementTerm[] agreementTerms = new AgreementTerm[agreementTermsXml.length];
        for (int i = 0; i < agreementTerms.length; i++) {
            AgreementTermType agreementTermXml = agreementTermsXml[i];
            agreementTerms[i] = this.elementParser.parseAgreementTerm(agreementTermXml);
        }
        this.slaModel.setAgreementTerms(agreementTerms);
    }

    protected void addInterfaces() throws Exception {
        InterfaceDeclrType[] interfacesXml = this.renderedSLATemplate.getSLATemplate().getInterfaceDeclrArray();
        InterfaceDeclr[] interfaces = new InterfaceDeclr[interfacesXml.length];
        for (int i = 0; i < interfaces.length; i++) {
            InterfaceDeclrType interfaceXml = interfacesXml[i];
            interfaces[i] = this.elementParser.parseInterfaceDeclraration(interfaceXml);
        }
        this.slaModel.setInterfaceDeclrs(interfaces);
    }

    protected void addParties() throws Exception {
        AgreementPartyType[] partiesXml = this.renderedSLATemplate.getSLATemplate().getPartyArray();
        Party[] parties = new Party[partiesXml.length];
        for (int i = 0; i < parties.length; i++) {
            AgreementPartyType agreementPartyXml = partiesXml[i];
            parties[i] = this.elementParser.parseAgreementParty(agreementPartyXml);
        }
        this.slaModel.setParties(parties);
    }

    protected void addVariableDeclarations() throws Exception {
        VariableDeclrType[] varDeclrsXml = this.renderedSLATemplate.getSLATemplate().getVariableDeclrArray();
        VariableDeclr[] varDeclrs = new VariableDeclr[varDeclrsXml.length];
        for (int i = 0; i < varDeclrs.length; i++) {
            VariableDeclrType varDeclrXml = varDeclrsXml[i];
            varDeclrs[i] = this.elementParser.parseVariableDeclr(varDeclrXml);
        }
        this.slaModel.setVariableDeclrs(varDeclrs);
    }

    protected void initSLAModel(String slaTemplateRendering) throws Exception {
        assert slaTemplateRendering != null : "The template to render is null!";
        this.renderedSLATemplate = SLATemplateDocument.Factory.parse(slaTemplateRendering);
        this.slaModel = new SLATemplate();

        // Annotation
        this.elementParser.setAnnotation(slaModel, this.renderedSLATemplate.getSLATemplate());

        // UUID
        assert this.renderedSLATemplate.getSLATemplate().getUUID() != null : "The template to render does not contain an UUID!";
        String uuid = this.renderedSLATemplate.getSLATemplate().getUUID();
        this.slaModel.setUuid(new STND(uuid));

        // ModelVersion
        // String modelVersion = this.renderedSLATemplate.getSLATemplate().getModelVersion();
        // TODO maybe provide some output if modelVersions do not fit

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
        // TODO Auto-generated method stub
        return false;
    }

}

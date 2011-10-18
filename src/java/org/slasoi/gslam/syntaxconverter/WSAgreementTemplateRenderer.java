package org.slasoi.gslam.syntaxconverter;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.AgreementContextType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.AgreementRoleType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.GuaranteeTermType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.ServiceDescriptionTermType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.TemplateDocument;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.TermCompositorType;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.SLATemplate;
import org.slasoi.slamodel.sla.VariableDeclr;

import eu.slaatsoi.slamodel.AgreementPartyType;
import eu.slaatsoi.slamodel.AgreementTermDocument;
import eu.slaatsoi.slamodel.AgreementTermType;
import eu.slaatsoi.slamodel.InterfaceDeclrDocument;
import eu.slaatsoi.slamodel.InterfaceDeclrType;
import eu.slaatsoi.slamodel.PartyDocument;
import eu.slaatsoi.slamodel.VariableDeclrDocument;
import eu.slaatsoi.slamodel.VariableDeclrType;

/**
 * Renders an SLA model template to WS-Agreement template.
 * @author Peter A. Chronz
 *
 */
public class WSAgreementTemplateRenderer extends SLATemplateRenderer {
    public static final String slasoiNamespace = "http://www.slaatsoi.eu/slamodel";
    public static final String wsagNamespace = "http://schemas.ggf.org/graap/2007/03/ws-agreement";

    protected TemplateDocument wsagTemplate = null;

    public WSAgreementTemplateRenderer() {
        this.elementRenderer = new XmlElementRenderer();
    }

    protected boolean validateSLARendering() {
        return this.wsagTemplate.validate();
    }

    protected void addAgreementTerms() throws Exception {
        TermCompositorType allXml = this.wsagTemplate.getTemplate().getTerms().getAll();

        AgreementTerm[] agreementTerms = this.slaModel.getAgreementTerms();
        for (int i = 0; i < agreementTerms.length; i++) {
            GuaranteeTermType guaranteeTermXml = allXml.addNewGuaranteeTerm();
            XmlObject sloXml = guaranteeTermXml.addNewServiceLevelObjective().addNewCustomServiceLevel();

            AgreementTerm agreementTerm = agreementTerms[i];
            AgreementTermType agreementTermXml =
                    (AgreementTermType) this.elementRenderer.renderAgreementTerm(agreementTerm);
            AgreementTermDocument agreementTermDocXml = AgreementTermDocument.Factory.newInstance();
            agreementTermDocXml.setAgreementTerm(agreementTermXml);

            XmlCursor sloCursor = sloXml.newCursor();
            sloCursor.toEndToken();
            XmlCursor agreementTermCursor = agreementTermDocXml.newCursor();
            agreementTermCursor.moveXmlContents(sloCursor);

            agreementTermCursor.dispose();
            sloCursor.dispose();

            // for WS-Agreement's requirement
            guaranteeTermXml.addNewBusinessValueList();
            guaranteeTermXml.setName("AgreeementTerm" + (i + 1));
        }
    }

    protected void initTargetModel() throws Exception {
        // create the instance
        this.wsagTemplate = TemplateDocument.Factory.newInstance();
        this.product = this.wsagTemplate;

        this.wsagTemplate.addNewTemplate();

        // UUID
        this.wsagTemplate.getTemplate().setTemplateId(this.slaModel.getUuid().getValue());

        // add the Context
        AgreementContextType contextXml = this.wsagTemplate.getTemplate().addNewContext();

        // Annotation
        this.elementRenderer.setAnnotation(slaModel, wsagTemplate.getTemplate().getContext());

        // ModelVersion
        XmlCursor contextCursor = contextXml.newCursor();
        contextCursor.toNextToken();
        contextCursor.beginElement(new QName(WSAgreementTemplateRenderer.slasoiNamespace, "ModelVersion"));
        contextCursor.insertChars(SLATemplate.$model_version);
        contextCursor.dispose();

        // wsag:Terms and wsag:All
        this.wsagTemplate.getTemplate().addNewTerms().addNewAll();

        // since it is a template we need CreationConstraints
        this.wsagTemplate.getTemplate().addNewCreationConstraints();

        // leave initiator and responder empty since it is unknown who is initiator and responder
        this.wsagTemplate.getTemplate().getContext().addNewAgreementInitiator();
        this.wsagTemplate.getTemplate().getContext().addNewAgreementResponder();
        // unknown
        this.wsagTemplate.getTemplate().getContext().setServiceProvider(AgreementRoleType.AGREEMENT_INITIATOR);
    }

    protected void addInterfaces() throws Exception {
        TermCompositorType allXml = this.wsagTemplate.getTemplate().getTerms().getAll();

        InterfaceDeclr[] interfaceDeclrs = this.slaModel.getInterfaceDeclrs();
        for (int i = 0; i < interfaceDeclrs.length; i++) {
            ServiceDescriptionTermType sdtXml = allXml.addNewServiceDescriptionTerm();
            XmlCursor sdtCursor = sdtXml.newCursor();
            sdtCursor.toEndToken();

            InterfaceDeclr interfaceDeclr = interfaceDeclrs[i];
            InterfaceDeclrDocument interfaceDeclrDocXml = InterfaceDeclrDocument.Factory.newInstance();
            InterfaceDeclrType interfaceDeclrXml =
                    (InterfaceDeclrType) this.elementRenderer.renderInterfaceDeclr(interfaceDeclr);
            interfaceDeclrDocXml.setInterfaceDeclr(interfaceDeclrXml);
            XmlCursor iFaceDeclrCursor = interfaceDeclrDocXml.newCursor();

            iFaceDeclrCursor.moveXmlContents(sdtCursor);

            iFaceDeclrCursor.dispose();
            sdtCursor.dispose();

            // add name and service-name to sdt, which is required by WS-Agreement
            sdtXml.setName("InterfaceDeclr" + (i + 1));
            sdtXml.setServiceName(interfaceDeclr.getId().getValue());
        }
    }

    protected void addParties() throws Exception {
        // get context
        AgreementContextType contextXml = this.wsagTemplate.getTemplate().getContext();

        // Parties
        Party[] parties = this.slaModel.getParties();
        for (int i = 0; i < parties.length; i++) {
            Party party = parties[i];
            PartyDocument partyDocXml = PartyDocument.Factory.newInstance();
            AgreementPartyType partyTypeXml = (AgreementPartyType) this.elementRenderer.renderParty(party);
            partyDocXml.setParty(partyTypeXml);
            XmlCursor partyCursor = partyDocXml.newCursor();
            XmlCursor contextCursor = contextXml.newCursor();
            contextCursor.toEndToken();
            partyCursor.moveXmlContents(contextCursor);
            partyCursor.dispose();
            contextCursor.dispose();
        }
    }

    protected void addVariableDeclarations() throws Exception {
        TermCompositorType allXml = this.wsagTemplate.getTemplate().getTerms().getAll();

        VariableDeclr[] variableDeclrs = this.slaModel.getVariableDeclrs();
        for (int i = 0; i < variableDeclrs.length; i++) {
            ServiceDescriptionTermType sdtXml = allXml.addNewServiceDescriptionTerm();

            VariableDeclr variableDeclr = variableDeclrs[i];
            VariableDeclrType variableDeclrXml =
                    (VariableDeclrType) this.elementRenderer.renderVariableDeclr(variableDeclr);
            VariableDeclrDocument variableDeclrDocXml = VariableDeclrDocument.Factory.newInstance();
            variableDeclrDocXml.setVariableDeclr(variableDeclrXml);

            XmlCursor variableDeclrCursor = variableDeclrDocXml.newCursor();
            XmlCursor sdtCursor = sdtXml.newCursor();
            sdtCursor.toEndToken();

            variableDeclrCursor.moveXmlContents(sdtCursor);

            variableDeclrCursor.dispose();
            sdtCursor.dispose();

            // add name and service-name to sdt as required by WS-Agreement
            sdtXml.setName(variableDeclr.getVar().getValue());
            // the sla model does not reference any special service with a VariableDeclr
            sdtXml.setServiceName("VariableDeclr");
        }
    }

    protected String serializeProduct() throws Exception {
        return this.wsagTemplate.xmlText();
    }

}

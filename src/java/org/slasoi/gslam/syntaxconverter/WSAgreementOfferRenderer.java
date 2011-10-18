package org.slasoi.gslam.syntaxconverter;

import java.util.Calendar;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.XmlCalendar;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.AgreementContextType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.AgreementOfferDocument;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.AgreementRoleType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.GuaranteeTermType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.ServiceDescriptionTermType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.TermCompositorType;
import org.slasoi.slamodel.primitives.UUID;
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
 * Renders the SLA model to WS-Agreement offers.
 * @author Peter A. Chronz
 *
 */
public class WSAgreementOfferRenderer extends SLARenderer {
    public static final String slasoiNamespace = "http://www.slaatsoi.eu/slamodel";
    public static final String wsagNamespace = "http://schemas.ggf.org/graap/2007/03/ws-agreement";

    protected AgreementOfferDocument wsagOffer = null;

    public WSAgreementOfferRenderer() {
        this.elementRenderer = new XmlElementRenderer();
    }

    protected void addAgreementTerms() throws Exception {
        TermCompositorType allXml = this.wsagOffer.getAgreementOffer().getTerms().getAll();

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

    protected void addInterfaces() throws Exception {
        TermCompositorType allXml = this.wsagOffer.getAgreementOffer().getTerms().getAll();

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
        AgreementContextType contextXml = this.wsagOffer.getAgreementOffer().getContext();

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
        TermCompositorType allXml = this.wsagOffer.getAgreementOffer().getTerms().getAll();

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

    protected void initTargetModel() throws Exception {
        // create the instance
        this.wsagOffer = AgreementOfferDocument.Factory.newInstance();
        this.product = this.wsagOffer;

        this.wsagOffer.addNewAgreementOffer();

        // UUID
        this.wsagOffer.getAgreementOffer().setAgreementId(this.slaModel.getUuid().getValue());

        // add the Context
        AgreementContextType contextXml = this.wsagOffer.getAgreementOffer().addNewContext();

        // Annotation
        this.elementRenderer.setAnnotation(slaModel, wsagOffer.getAgreementOffer().getContext());

        // ModelVersion
        XmlCursor contextCursor = contextXml.newCursor();
        contextCursor.toNextToken();
        contextCursor.beginElement(new QName(WSAgreementTemplateRenderer.slasoiNamespace, "ModelVersion"));
        contextCursor.insertChars(SLATemplate.$model_version);
        contextCursor.dispose();

        // AgreedAt
        contextCursor = contextXml.newCursor();
        contextCursor.toNextToken();
        contextCursor.beginElement(new QName(WSAgreementTemplateRenderer.slasoiNamespace, "AgreedAt"));
        Calendar agreedAt = this.slaModel.getAgreedAt().getValue();
        XmlCalendar agreedAtXml = new XmlCalendar(agreedAt.getTime());
        contextCursor.insertChars(agreedAtXml.toString());
        contextCursor.dispose();

        // EffectiveFrom
        contextCursor = contextXml.newCursor();
        contextCursor.toNextToken();
        contextCursor.beginElement(new QName(WSAgreementTemplateRenderer.slasoiNamespace, "EffectiveFrom"));
        Calendar effectiveFrom = this.slaModel.getEffectiveFrom().getValue();
        XmlCalendar effectiveFromXml = new XmlCalendar(effectiveFrom.getTime());
        contextCursor.insertChars(effectiveFromXml.toString());
        contextCursor.dispose();

        // EffectiveUntil
        contextCursor = contextXml.newCursor();
        contextCursor.toNextToken();
        contextCursor.beginElement(new QName(WSAgreementTemplateRenderer.slasoiNamespace, "EffectiveUntil"));
        Calendar effectiveUntil = this.slaModel.getEffectiveUntil().getValue();
        XmlCalendar effectiveUntilXml = new XmlCalendar(effectiveUntil.getTime());
        contextCursor.insertChars(effectiveUntilXml.toString());
        contextCursor.dispose();

        // wsag:Terms and wsag:All
        this.wsagOffer.getAgreementOffer().addNewTerms().addNewAll();

        // leave initiator and responder empty since it is unknown who is initiator and responder
        this.wsagOffer.getAgreementOffer().getContext().addNewAgreementInitiator();
        this.wsagOffer.getAgreementOffer().getContext().addNewAgreementResponder();
        // unknown
        this.wsagOffer.getAgreementOffer().getContext().setServiceProvider(AgreementRoleType.AGREEMENT_INITIATOR);

        // TemplateId
        UUID templateId = this.slaModel.getTemplateId();
        this.wsagOffer.getAgreementOffer().getContext().setTemplateId(templateId.getValue());
    }

    protected boolean validateSLARendering() {
        return this.wsagOffer.validate();
    }

    protected String serializeProduct() throws Exception {
        return this.wsagOffer.xmlText();
    }

}

package org.slasoi.gslam.syntaxconverter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.AgreementTemplateType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.GuaranteeTermType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.ServiceDescriptionTermType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.ServiceLevelObjectiveType;
import org.ggf.schemas.graap.x2007.x03.wsAgreement.TemplateDocument;
import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.SLATemplate;
import org.slasoi.slamodel.sla.VariableDeclr;
import org.w3c.dom.Node;

import eu.slaatsoi.slamodel.AgreementTermDocument;
import eu.slaatsoi.slamodel.InterfaceDeclrDocument;
import eu.slaatsoi.slamodel.PartyDocument;
import eu.slaatsoi.slamodel.VariableDeclrDocument;

/**
 * Parses WS-Agreement templates to SLA model.
 * @author Peter A. Chronz
 *
 */
public class WSAgreementTemplateParser extends SLATemplateParser {
    protected static final String slasoiNamespace = "http://www.slaatsoi.eu/slamodel";
    protected static final String wsagNamespace = "http://schemas.ggf.org/graap/2007/03/ws-agreement";

    protected TemplateDocument wsagSLA;

    public WSAgreementTemplateParser() {
        this.elementParser = new XmlElementParser();
    }

    // TEMPLATE METHODS

    protected void addAgreementTerms() throws Exception {
        AgreementTemplateType templateXml = this.wsagSLA.getTemplate();
        GuaranteeTermType[] guaranteeTermArray = templateXml.getTerms().getAll().getGuaranteeTermArray();
        List<AgreementTerm> allGuaranteeTerms = new LinkedList<AgreementTerm>();

        for (int i = 0; i < guaranteeTermArray.length; i++) {
            GuaranteeTermType guaranteeTermXml = guaranteeTermArray[i];
            ServiceLevelObjectiveType sloXml = guaranteeTermXml.getServiceLevelObjective();
            // slasoi:AgreementTerm
            String agreementTermsPath =
                    "*[local-name()=\"AgreementTerm\" and namespace-uri()='"
                            + WSAgreementTemplateParser.slasoiNamespace + "']";
            XmlObject[] agreementTermsXml = sloXml.getCustomServiceLevel().selectPath(agreementTermsPath);
            AgreementTerm[] agreementTerms = new AgreementTerm[agreementTermsXml.length];
            for (int j = 0; j < agreementTerms.length; j++) {
                Node agreementTermNode = agreementTermsXml[j].getDomNode();
                AgreementTermDocument agreementTermDoc = AgreementTermDocument.Factory.parse(agreementTermNode);
                agreementTerms[j] = this.elementParser.parseAgreementTerm(agreementTermDoc.getAgreementTerm());
            }
            allGuaranteeTerms.addAll(Arrays.asList(agreementTerms));
        }

        AgreementTerm[] allGuarantees = new AgreementTerm[allGuaranteeTerms.size()];
        for (int i = 0; i < allGuaranteeTerms.size(); i++) {
            allGuarantees[i] = allGuaranteeTerms.get(i);
        }
        this.slaModel.setAgreementTerms(allGuarantees);
    }

    protected void addInterfaces() throws Exception {
        AgreementTemplateType templateXml = this.wsagSLA.getTemplate();
        ServiceDescriptionTermType[] sdts = templateXml.getTerms().getAll().getServiceDescriptionTermArray();
        LinkedList<InterfaceDeclr> interfaceDeclarationList = new LinkedList<InterfaceDeclr>();
        for (int i = 0; i < sdts.length; i++) {
            ServiceDescriptionTermType sdtXml = sdts[i];
            XmlObject[] interfaceDeclrs =
                    sdtXml.selectPath("*[local-name()=\"InterfaceDeclr\" and namespace-uri()='"
                            + WSAgreementTemplateParser.slasoiNamespace + "']");
            for (int j = 0; j < interfaceDeclrs.length; j++) {
                Node iFaceDeclrNode = interfaceDeclrs[j].getDomNode();
                InterfaceDeclrDocument iFaceDeclrDoc = InterfaceDeclrDocument.Factory.parse(iFaceDeclrNode);
                InterfaceDeclr interfaceDeclr =
                        this.elementParser.parseInterfaceDeclraration(iFaceDeclrDoc.getInterfaceDeclr());
                interfaceDeclarationList.add(interfaceDeclr);
            }
        }

        InterfaceDeclr[] interfaceDeclrs = new InterfaceDeclr[interfaceDeclarationList.size()];
        for (int i = 0; i < interfaceDeclrs.length; i++) {
            interfaceDeclrs[i] = interfaceDeclarationList.get(i);
        }

        this.slaModel.setInterfaceDeclrs(interfaceDeclrs);
    }

    protected void addParties() throws Exception {
        // /wsag:Template/wsag:Context/slasoi:Party
        String partyPath =
                "/*[local-name()=\"Template\" and namespace-uri()='" + WSAgreementTemplateParser.wsagNamespace
                        + "']/*[local-name()=\"Context\" and namespace-uri()='"
                        + WSAgreementTemplateParser.wsagNamespace + "']/*[local-name()=\"Party\" and namespace-uri()='"
                        + WSAgreementTemplateParser.slasoiNamespace + "']";
        XmlObject[] partiesXml = this.wsagSLA.selectPath(partyPath);
        Party[] parties = new Party[partiesXml.length];
        for (int i = 0; i < partiesXml.length; i++) {
            XmlObject anyXml = partiesXml[i];
            PartyDocument partyXml = PartyDocument.Factory.parse(anyXml.getDomNode());
            Party party = this.elementParser.parseAgreementParty(partyXml.getParty());
            parties[i] = party;
        }
        this.slaModel.setParties(parties);
    }

    protected void addVariableDeclarations() throws Exception {
        // get the VariableDeclrarations
        // //wsag:ServiceDescriptionTerm/slasoi:VariableDeclr
        String variableDeclrPath =
                "//*[local-name()=\"ServiceDescriptionTerm\" and namespace-uri()='"
                        + WSAgreementTemplateParser.wsagNamespace
                        + "']/*[local-name()=\"VariableDeclr\" and namespace-uri()='"
                        + WSAgreementTemplateParser.slasoiNamespace + "']";
        XmlObject[] variableDeclararationsXml = this.wsagSLA.selectPath(variableDeclrPath);
        VariableDeclr[] variableDeclarations = new VariableDeclr[variableDeclararationsXml.length];
        for (int i = 0; i < variableDeclararationsXml.length; i++) {
            Node domNode = variableDeclararationsXml[i].getDomNode();
            VariableDeclrDocument variableDeclrDocument = VariableDeclrDocument.Factory.parse(domNode);
            variableDeclarations[i] = this.elementParser.parseVariableDeclr(variableDeclrDocument.getVariableDeclr());
        }
        this.slaModel.setVariableDeclrs(variableDeclarations);
    }

    protected void initSLAModel(String slaTemplateRendering) throws XmlException {
        this.wsagSLA = TemplateDocument.Factory.parse(slaTemplateRendering);

        // create the target object
        this.slaModel = new SLATemplate();

        // set the template's UUID
        String templateId = this.wsagSLA.getTemplate().getTemplateId();
        UUID uuid = new UUID(templateId);
        this.slaModel.setUuid(uuid);

        // verify the model version
        // //slasoi:ModelVersion
        String modelVersionPath =
                "//*[local-name()=\"ModelVersion\" and namespace-uri()='" + WSAgreementTemplateParser.slasoiNamespace
                        + "']";
        XmlObject[] modelNode = this.wsagSLA.selectPath(modelVersionPath);
        String modelVersion = modelNode[0].getDomNode().getFirstChild().getNodeValue();
        if (!SLATemplate.$model_version.equals(modelVersion)) {
            // HELL BREAKS LOOSE HERE
        }
    }

    protected boolean validateSLAModel() {
        // TODO Auto-generated method stub
        return true;
    }

    // NON-TEMPLATE METHODS

}

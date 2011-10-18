package org.slasoi.gslam.syntaxconverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.slasoi.slamodel.core.Annotated;
import org.slasoi.slamodel.core.CompoundConstraintExpr;
import org.slasoi.slamodel.core.CompoundDomainExpr;
import org.slasoi.slamodel.core.ConstraintExpr;
import org.slasoi.slamodel.core.DomainExpr;
import org.slasoi.slamodel.core.EventExpr;
import org.slasoi.slamodel.core.FunctionalExpr;
import org.slasoi.slamodel.core.SimpleDomainExpr;
import org.slasoi.slamodel.core.TypeConstraintExpr;
import org.slasoi.slamodel.primitives.BOOL;
import org.slasoi.slamodel.primitives.CONST;
import org.slasoi.slamodel.primitives.Expr;
import org.slasoi.slamodel.primitives.ID;
import org.slasoi.slamodel.primitives.LIST;
import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.primitives.TIME;
import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.primitives.ValueExpr;
import org.slasoi.slamodel.service.Interface;
import org.slasoi.slamodel.service.ResourceType;
import org.slasoi.slamodel.service.ServiceRef;
import org.slasoi.slamodel.service.Interface.Operation;
import org.slasoi.slamodel.service.Interface.Specification;
import org.slasoi.slamodel.service.Interface.Operation.Property;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.CustomAction;
import org.slasoi.slamodel.sla.Customisable;
import org.slasoi.slamodel.sla.Endpoint;
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.InterfaceRef;
import org.slasoi.slamodel.sla.Invocation;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.VariableDeclr;
import org.slasoi.slamodel.sla.Guaranteed.Action;
import org.slasoi.slamodel.sla.Guaranteed.State;
import org.slasoi.slamodel.sla.Guaranteed.Action.Defn;
import org.slasoi.slamodel.sla.Party.Operative;
import org.slasoi.slamodel.sla.business.ComponentProductOfferingPrice;
import org.slasoi.slamodel.sla.business.Penalty;
import org.slasoi.slamodel.sla.business.PriceModification;
import org.slasoi.slamodel.sla.business.Product;
import org.slasoi.slamodel.sla.business.ProductOfferingPrice;
import org.slasoi.slamodel.sla.business.Termination;
import org.slasoi.slamodel.sla.business.TerminationClause;
import org.w3c.dom.Node;

import eu.slaatsoi.slamodel.AgreementPartyType;
import eu.slaatsoi.slamodel.AgreementTermType;
import eu.slaatsoi.slamodel.CONSTType;
import eu.slaatsoi.slamodel.ComponentProdOfferingPriceType;
import eu.slaatsoi.slamodel.CompoundConstraintExprType;
import eu.slaatsoi.slamodel.CompoundDomainExprType;
import eu.slaatsoi.slamodel.ConstraintExprType;
import eu.slaatsoi.slamodel.CustomisableType;
import eu.slaatsoi.slamodel.DomainExprDocument;
import eu.slaatsoi.slamodel.DomainExprType;
import eu.slaatsoi.slamodel.EndpointType;
import eu.slaatsoi.slamodel.EventExprDocument;
import eu.slaatsoi.slamodel.EventExprType;
import eu.slaatsoi.slamodel.ExprType;
import eu.slaatsoi.slamodel.FuncExprDocument;
import eu.slaatsoi.slamodel.FuncExprType;
import eu.slaatsoi.slamodel.GuaranteedActionDefnType;
import eu.slaatsoi.slamodel.GuaranteedActionType;
import eu.slaatsoi.slamodel.GuaranteedStateType;
import eu.slaatsoi.slamodel.GuaranteedType;
import eu.slaatsoi.slamodel.IdValueExprEntryType;
import eu.slaatsoi.slamodel.InterfaceDeclrType;
import eu.slaatsoi.slamodel.InterfaceOperationPropertyType;
import eu.slaatsoi.slamodel.InterfaceOperationType;
import eu.slaatsoi.slamodel.InterfaceRefType;
import eu.slaatsoi.slamodel.InterfaceSpecType;
import eu.slaatsoi.slamodel.InvocationType;
import eu.slaatsoi.slamodel.ListValueExprType;
import eu.slaatsoi.slamodel.OperativeType;
import eu.slaatsoi.slamodel.PenaltyActionType;
import eu.slaatsoi.slamodel.PriceModificationType;
import eu.slaatsoi.slamodel.ProductOfferingPriceType;
import eu.slaatsoi.slamodel.ProductType;
import eu.slaatsoi.slamodel.ServiceRefType;
import eu.slaatsoi.slamodel.SimpleDomainExprType;
import eu.slaatsoi.slamodel.TerminationActionType;
import eu.slaatsoi.slamodel.TerminationClauseType;
import eu.slaatsoi.slamodel.TypeConstraintExprType;
import eu.slaatsoi.slamodel.ValueExprType;
import eu.slaatsoi.slamodel.VariableDeclrType;

/**
 * Provides all necessary methods to parse xml-based elements in the SLA model.
 * @author Peter A. Chronz
 *
 */
public class XmlElementParser extends ElementParser {

    public AgreementTerm parseAgreementTerm(Object agreementTermObject) throws Exception {
        assert agreementTermObject != null : "The agreement term to be parsed is null!";
        AgreementTermType agreementTermXml = (AgreementTermType) agreementTermObject;

        // ID
        assert agreementTermXml.getID() != null : "The agreement term to be parsed does not contain an id!";
        String idString = agreementTermXml.getID().trim();
        ID id = new ID(idString);

        // Precondition
        ConstraintExprType preconditionXml = agreementTermXml.getPrecondition();
        ConstraintExpr precondition = null;
        if (preconditionXml != null) {
            precondition = parseConstraintExpr(preconditionXml);
        }

        // VariableDeclr
        VariableDeclrType[] variableDeclrsXml = agreementTermXml.getVariableDeclrArray();
        VariableDeclr[] variableDeclrs = new VariableDeclr[variableDeclrsXml.length];
        for (int i = 0; i < variableDeclrsXml.length; i++) {
            variableDeclrs[i] = parseVariableDeclr(variableDeclrsXml[i]);
        }

        // Guaranteed
        GuaranteedType[] guaranteedXmlArray = agreementTermXml.getGuaranteedArray();
        Guaranteed[] guaranteedArray = new Guaranteed[guaranteedXmlArray.length];
        for (int i = 0; i < guaranteedXmlArray.length; i++) {
            GuaranteedType guaranteedXml = guaranteedXmlArray[i];

            // identify the type of guarantee
            if (guaranteedXml.isSetAction()) {
                guaranteedArray[i] = parseGuaranteedAction(guaranteedXml.getAction());
            }
            else if (guaranteedXml.isSetState()) {
                guaranteedArray[i] = parseGuaranteedState(guaranteedXml.getState());
            }

            // Annotation
            setAnnotation(guaranteedArray[i], guaranteedXml);
        }
        
        AgreementTerm agreementTerm = new AgreementTerm(id, precondition, variableDeclrs, guaranteedArray);

        // Annotation
        setAnnotation(agreementTerm, agreementTermXml);

        return agreementTerm;
    }

    public Action parseGuaranteedAction(Object guaranteedActionObject) throws Exception {
        assert guaranteedActionObject != null : "The guaranteed action to be parsed is null!";
        GuaranteedActionType guaranteedActionXml = (GuaranteedActionType) guaranteedActionObject;

        Guaranteed.Action guaranteedAction = null;

        // ID
        assert guaranteedActionXml.getID() != null : "The guaranteed action to be parsed does not contain an id!";
        String idString = guaranteedActionXml.getID().trim();
        ID id = new ID(idString);

        // ActorRef
        assert guaranteedActionXml.getActorRef() != null : "The guaranteed action to be parsed does not contain an actor referecnce!";
        String actorRefString = guaranteedActionXml.getActorRef().trim();
        ID actorRef = new ID(actorRefString);

        // Policy
        assert guaranteedActionXml.getPolicy() != null : "The guaranteed action to be parsed does not contain a policy!";
        String policyString = guaranteedActionXml.getPolicy().trim();
        STND policy = new STND(policyString);

        // Precondition
        assert guaranteedActionXml.getPrecondition() != null : "The guaranteed action to be parsed does not contain a precondition!";
        EventExprType preconditionXml = guaranteedActionXml.getPrecondition();
        EventExpr precondition = parseEventExpr(preconditionXml);

        // Postcondition
        assert guaranteedActionXml.getPostcondition() != null : "The guaranteed action to be parsed does not contain a postcondition!";
        GuaranteedActionDefnType postconditionXml = guaranteedActionXml.getPostcondition();
        Guaranteed.Action.Defn postcondition = parseGuaranteedActionDefn(postconditionXml);
        parseAnnotatedProperties(postcondition, postconditionXml.getProperties());

        guaranteedAction = new Guaranteed.Action(id, actorRef, policy, precondition, postcondition);

        return guaranteedAction;
    }

    public Defn parseGuaranteedActionDefn(Object guaranteedActionDefnObject) throws Exception {
        assert guaranteedActionDefnObject != null : "The guaranteed action definition to be parsed is null!";
        GuaranteedActionDefnType guaranteedActionDefnXml = (GuaranteedActionDefnType) guaranteedActionDefnObject;

        Defn defn = null;

        if (guaranteedActionDefnXml.isSetInvocation()) {
            defn = parseInvocation(guaranteedActionDefnXml.getInvocation());
            setAnnotation(defn, guaranteedActionDefnXml.getInvocation());
        }
        else if (guaranteedActionDefnXml.isSetCompoundAction()) {
            defn = parseCompoundAction(guaranteedActionDefnXml.getCompoundAction());
            setAnnotation(defn, guaranteedActionDefnXml.getCompoundAction());
        }
        else if (guaranteedActionDefnXml.isSetCustomAction()) {
            defn = parseCustomAction(guaranteedActionDefnXml.getCustomAction());
            setAnnotation(defn, guaranteedActionDefnXml.getCustomAction());
        }
        else if (guaranteedActionDefnXml.isSetTerminationAction()) {
            defn = parseTerminationAction(guaranteedActionDefnXml.getTerminationAction());
            setAnnotation(defn, guaranteedActionDefnXml.getTerminationAction());
        }
        else if (guaranteedActionDefnXml.isSetPenaltyAction()) {
            defn = parsePenaltyAction(guaranteedActionDefnXml.getPenaltyAction());
            setAnnotation(defn, guaranteedActionDefnXml.getPenaltyAction());
        }
        else if (guaranteedActionDefnXml.isSetProductOfferingPrice()) {
            defn = parseProductOfferingPrice(guaranteedActionDefnXml.getProductOfferingPrice());
            setAnnotation(defn, guaranteedActionDefnXml.getProductOfferingPrice());
        }
        else if (guaranteedActionDefnXml.isSetTerminationClause()) {
            defn = parseTerminationClause(guaranteedActionDefnXml.getTerminationClause());
            setAnnotation(defn, guaranteedActionDefnXml.getTerminationClause());
        }
        else
            return null;

        return defn;
    }

    public Defn parseTerminationClause(Object terminationClauseX) {
        assert terminationClauseX != null : "The termination clause to be parsed is null!";
        TerminationClauseType terminationClauseXml = (TerminationClauseType) terminationClauseX;
        CONSTType feesXml = terminationClauseXml.getFees();
        
        assert feesXml != null : "Fees are null!";
        String clauseId = terminationClauseXml.getID();
        
        assert clauseId != null : "ID is missing!";
        String id = clauseId.trim();
        
        assert terminationClauseXml.getNotificationMethod() != null : "Notification method is null!";
        String notificationMethod = terminationClauseXml.getNotificationMethod().trim();
        
        assert terminationClauseXml.getNotificationPeriod() !=null : "Notification period is null!";
        CONSTType notificationPeriod = terminationClauseXml.getNotificationPeriod();
        
        assert terminationClauseXml.getTerminationClauseDescription() != null : "Termination clause description is null";
        String terminationClauseDescription = terminationClauseXml.getTerminationClauseDescription().trim();
        
        assert terminationClauseXml.getTerminationClauseType() != null : "Termination clause type is null";
        String terminationClauseType = terminationClauseXml.getTerminationClauseType().trim();
        
        assert terminationClauseXml.getTerminationInitiator() != null : "Termination initiator is null";
        String terminationInitiator = terminationClauseXml.getTerminationInitiator().trim();

        TerminationClause terminationClause =
                new TerminationClause(new ID(id), new STND(terminationInitiator), terminationClauseType,
                        terminationClauseDescription, new CONST(notificationPeriod.getValue(), new STND(
                                notificationPeriod.getDatatype())));
        terminationClause.setFees(new CONST(feesXml.getValue(), new STND(feesXml.getDatatype())));
        terminationClause.setNotificationMethod(new STND(notificationMethod));

        return terminationClause;
    }

    public Defn parseProductOfferingPrice(Object productOfferingPriceX) throws Exception {
        assert productOfferingPriceX != null : "The product offering price to be parsed is null!";
        ProductOfferingPriceType productOfferingPriceXml = (ProductOfferingPriceType) productOfferingPriceX;
        String billingFrequencyString = productOfferingPriceXml.getBillingFrequency().trim();
        ComponentProdOfferingPriceType[] componentProdOfferingPricesXml =
                productOfferingPriceXml.getComponentProdOfferingPriceArray();
        String idString = productOfferingPriceXml.getID().trim();
        String nameString = productOfferingPriceXml.getName().trim();
        ProductType productXml = productOfferingPriceXml.getProduct();
        Calendar validFrom = productOfferingPriceXml.getValidFrom();
        Calendar validUntil = productOfferingPriceXml.getValidUntil();

        ComponentProductOfferingPrice[] componentProdOfferingPrices =
                new ComponentProductOfferingPrice[componentProdOfferingPricesXml.length];
        for (int i = 0; i < componentProdOfferingPrices.length; i++) {
            componentProdOfferingPrices[i] = parseComponentProdOfferingPrice(componentProdOfferingPricesXml[i]);
        }

        TIME validFromTime = new TIME(validFrom);
        TIME validUntilTime = new TIME(validUntil);

        ProductOfferingPrice productOfferingPrice =
                new ProductOfferingPrice(new ID(idString), nameString, validFromTime, validUntilTime, new STND(
                        billingFrequencyString), componentProdOfferingPrices);

        if (productOfferingPriceXml.isSetProduct())
            productOfferingPrice.setProduct(parseProduct(productXml));

        // Annotation
        setAnnotation(productOfferingPrice, productOfferingPriceXml);

        return productOfferingPrice;
    }

    public Product parseProduct(Object productX) throws Exception {
        assert productX != null : "The product to be parsed is null!";
        ProductType productXml = (ProductType) productX;

        String id = productXml.getID().trim();
        String name = productXml.getName().trim();

        Product product = new Product(new ID(id), name);

        // Annotation
        setAnnotation(product, productXml);

        return product;
    }

    public ComponentProductOfferingPrice parseComponentProdOfferingPrice(Object componentProdOfferingPrice) {
        assert componentProdOfferingPrice != null : "The component product offering price to be parsed is null!";
        ComponentProdOfferingPriceType componentProdOfferingPriceXml =
                (ComponentProdOfferingPriceType) componentProdOfferingPrice;
        String id = componentProdOfferingPriceXml.getID().trim();
        CONSTType price = componentProdOfferingPriceXml.getPrice();
        PriceModificationType[] priceModificationArray = componentProdOfferingPriceXml.getPriceModificationArray();
        String priceType = componentProdOfferingPriceXml.getPriceType().trim();
        CONSTType quantity = componentProdOfferingPriceXml.getQuantity();

        STND datatype = null;
        if (quantity.isSetDatatype()) {
            datatype = new STND(quantity.getDatatype());
        }
        ComponentProductOfferingPrice componentProductOfferingPrice =
                new ComponentProductOfferingPrice(new ID(id), new STND(priceType), new CONST(price.getValue(),
                        new STND(price.getDatatype())), new CONST(quantity.getValue(), datatype));

        PriceModification[] priceModification = new PriceModification[priceModificationArray.length];
        for (int i = 0; i < priceModification.length; i++) {
            priceModification[i] = parsePriceModification(priceModificationArray[i]);
        }
        componentProductOfferingPrice.setPriceModifications(priceModification);

        return componentProductOfferingPrice;
    }

    public PriceModification parsePriceModification(Object priceModification) {
        assert priceModification != null : "The price modification to be parsed is null!";
        PriceModificationType priceModificationXml = (PriceModificationType) priceModification;
        CONSTType value = priceModificationXml.getValue();
        String type = priceModificationXml.getType().trim();

        return new PriceModification(new STND(type), new CONST(value.getValue(), new STND(value.getDatatype())));
    }

    public Defn parsePenaltyAction(Object penaltyX) {
        assert penaltyX != null : "The penalty to be parsed is null!";
        PenaltyActionType penaltyActionXml = (PenaltyActionType) penaltyX;
        CONSTType price = penaltyActionXml.getPrice();
        String dataType = price.getDatatype();
        if (dataType == null)
            return new Penalty(new ID(price.getValue()));
        else
        	return new Penalty(new CONST(price.getValue(), new STND(price.getDatatype())));
    }

    public Defn parseTerminationAction(Object terminationAction) {
        assert terminationAction != null : "The termination action to be parsed is null!";
        TerminationActionType terminationActionXml = (TerminationActionType) terminationAction;

        String name = terminationActionXml.getName().trim();
        String terminationClauseID = terminationActionXml.getTerminationClauseID().trim();

        return new Termination(name, new ID(terminationClauseID));
    }

    public Defn parseCustomAction(Object customActionObject) {
        return new CustomAction();
    }

    public Defn parseCompoundAction(Object compoundAction) {
        // TODO Auto-generated method stub
        return null;
    }

    public Defn parseBusinessAction(Object businessActionObject) {
        // TODO Auto-generated method stub
        return null;
    }

    public Invocation parseInvocation(Object invocationObject) throws Exception {
        assert invocationObject != null : "The invocation to be parsed is null!";
        InvocationType invocationXml = (InvocationType) invocationObject;
        // Endpoint
        String endpointString = invocationXml.getEndpoint().trim();
        ID endpoint = new ID(endpointString);

        // Operation
        String operationString = invocationXml.getOperation().trim();
        ID operation = new ID(operationString);

        // Create instance
        Invocation invocation = new Invocation(operation);

        // Parameters
        IdValueExprEntryType[] parametersXml = invocationXml.getParameters().getEntryArray();
        for (int i = 0; i < parametersXml.length; i++) {
            IdValueExprEntryType parameterXml = parametersXml[i];

            // Key
            String keyString = parameterXml.getKey().trim();
            ID key = new ID(keyString);

            // Value
            ValueExprType valueXml = parameterXml.getValue();
            ValueExpr value = parseValueExpr(valueXml);

            invocation.setParameterValue(key, value);
        }

        // set remaining attributes
        invocation.setEndpointId(endpoint);
        invocation.setOperationId(operation);

        return invocation;
    }

    public State parseGuaranteedState(Object guaranteedStateObject) throws Exception {
        assert guaranteedStateObject != null : "The guaranteed state to be parsed is null!";
        GuaranteedStateType guaranteedStateXml = (GuaranteedStateType) guaranteedStateObject;

        // ID
        String idString = guaranteedStateXml.getID().trim();
        ID id = new ID(idString);

        // Priority
        CONSTType priorityXml = guaranteedStateXml.getPriority();
        CONST priorityConst = null;
        if (!priorityXml.isNil()) {
            String priorityValue = priorityXml.getValue().trim();
            String priorityType = priorityXml.getDatatype().trim();
            if (priorityType != null)
                priorityConst = new CONST(priorityValue, new STND(priorityType));
            else
                priorityConst = new CONST("null", null);
        }

        // Constraint
        ConstraintExprType constraintXml = guaranteedStateXml.getConstraint();
        ConstraintExpr constraint = parseConstraintExpr(constraintXml);

        State guaranteedState = new Guaranteed.State(id, constraint);

        // Precondition
        if (guaranteedStateXml.isSetPrecondition()) {
            ConstraintExpr precondition = parseConstraintExpr(guaranteedStateXml.getPrecondition());
            guaranteedState.setPrecondition(precondition);
        }

        // set the remaining attributes
        guaranteedState.setPriority(priorityConst);

        return guaranteedState;
    }

    public Party parseAgreementParty(Object partyObject) throws Exception {
        assert partyObject != null : "The party to be parsed is null!";
        AgreementPartyType partyXml = (AgreementPartyType) partyObject;
        // ID
        String idString = partyXml.getID().trim();
        ID id = new ID(idString);

        // Role
        Object roleXml = partyXml.getRole();
        STND roleStnd = new STND(roleXml.toString());

        Party party = new Party(id, roleStnd);

        // Operative
        OperativeType[] operativesXml = partyXml.getOperativeArray();
        Operative[] operatives = new Operative[operativesXml.length];
        for (int i = 0; i < operativesXml.length; i++) {
            OperativeType operativeType = operativesXml[i];
            Operative operative = parseOperative(operativeType);
            operatives[i] = operative;
        }
        party.setOperatives(operatives);

        // Annotation
        setAnnotation(party, partyXml);

        return party;
    }

    public Operative parseOperative(Object operativeObject) throws Exception {
        assert operativeObject != null : "The operative to be parsed is null!";
        OperativeType operativeXml = (OperativeType) operativeObject;

        // ID
        String idString = operativeXml.getID().trim();
        ID id = new ID(idString);

        // create the instance
        Operative operative = new Operative(id);

        // Annotation
        setAnnotation(operative, operativeXml);

        return operative;
    }

    public void setAnnotation(Annotated annotated, Object annotatedObject) throws Exception {
        assert annotated != null : "The annotated to be parsed is null!";
        assert annotatedObject != null : "The annotated to be parsed is null!";
        XmlObject annotatedXml = (XmlObject) annotatedObject;

        // XMLBeans does not recognize extended types as Annotated; do it using XPath

        // Properties
        // slasoi:Properties
        String propertiesPath =
                "*[local-name()=\"Properties\" and namespace-uri()='" + WSAgreementTemplateParser.slasoiNamespace
                        + "']";
        XmlObject[] propertiesMaps = annotatedXml.selectPath(propertiesPath);
        if (propertiesMaps.length == 1) {
            XmlObject propertiesMap = propertiesMaps[0];
            parseAnnotatedProperties(annotated, propertiesMap);
        }

        // Description
        // slasoi:Text
        String descriptionPath =
                "*[local-name()=\"Text\" and namespace-uri()='" + WSAgreementTemplateParser.slasoiNamespace + "']";
        XmlObject[] descObjects = annotatedXml.selectPath(descriptionPath);
        if (descObjects.length == 1) {
            if (!descObjects[0].isNil()) {
                XmlCursor newCursor = descObjects[0].newCursor();
                newCursor.toFirstChild();
                String description = newCursor.getTextValue().trim();
                if (!description.equals("")) {
                    annotated.setDescr(description);
                }
            }
        }
    }

    public void parseAnnotatedProperties(Annotated annotated, Object propertiesMapObject) {
        assert annotated != null : "The annotated properties to be parsed are null!";
        assert propertiesMapObject != null : "The annotated properties to be parsed are null!";
        XmlObject propertiesMapXml = (XmlObject) propertiesMapObject;
        // slasoi:Entry
        String entryPath =
                "*[local-name()=\"Entry\" and namespace-uri()='" + WSAgreementTemplateParser.slasoiNamespace + "']";
        XmlObject[] entries = propertiesMapXml.selectPath(entryPath);
        for (int i = 0; i < entries.length; i++) {
            XmlObject entryXml = entries[i];
            String keyValue = null;
            String valueValue = null;

            // get the key
            // slasoi:Key
            String keyPath =
                    "*[local-name()=\"Key\" and namespace-uri()='" + WSAgreementTemplateParser.slasoiNamespace + "']";
            XmlObject[] keysXml = entryXml.selectPath(keyPath);
            if (keysXml.length == 1) {
                XmlCursor keyCursor = keysXml[0].newCursor();
                keyCursor.toFirstChild();
                keyValue = keyCursor.getTextValue().trim();
            }
            else
                break;

            // get the value
            // slasoi:Value
            String valuePath =
                    "*[local-name()=\"Value\" and namespace-uri()='" + WSAgreementTemplateParser.slasoiNamespace + "']";
            XmlObject[] valuesXml = entryXml.selectPath(valuePath);
            if (valuesXml.length == 1) {
                XmlCursor valueCursor = valuesXml[0].newCursor();
                valueCursor.toFirstChild();
                valueValue = valueCursor.getTextValue().trim();
            }
            else
                break;

            STND stnd = new STND(keyValue);
            annotated.setPropertyValue(stnd, valueValue);
        }
    }

    public InterfaceDeclr parseInterfaceDeclraration(Object interfaceDeclrObject) throws Exception {
        assert interfaceDeclrObject != null : "The interface declaration to be parsed is null!";
        InterfaceDeclrType iFaceDeclrXml = (InterfaceDeclrType) interfaceDeclrObject;

        // ID
        String idString = iFaceDeclrXml.getID().trim();
        ID id = new ID(idString);

        // ProviderRef
        String providerRefString = (String) iFaceDeclrXml.getProviderRef().trim();
        ID providerRef = new ID(providerRefString);

        // Endpoints
        EndpointType[] endpointsXml = iFaceDeclrXml.getEndpointArray();
        Endpoint[] endpoints = new Endpoint[endpointsXml.length];
        for (int i = 0; i < endpointsXml.length; i++) {
            EndpointType endpointXml = endpointsXml[i];

            // ID
            String endpointIdString = endpointXml.getID().trim();
            ID endpointId = new ID(endpointIdString);

            // Location
            String locationString = endpointXml.getLocation().trim();
            UUID location = new UUID(locationString);

            // Protocol
            Object protocolString = endpointXml.getProtocol();
            STND protocol = new STND(((String) protocolString).trim());

            // create the instance
            Endpoint endpoint = new Endpoint(endpointId, protocol);

            // set the remaining attributes
            endpoint.setLocation(location);

            // Annotation
            setAnnotation(endpoint, endpointXml);

            endpoints[i] = endpoint;
        }

        // Interface
        org.slasoi.slamodel.service.Interface iFace = null;
        if (iFaceDeclrXml.getInterface().isSetInterfaceRef()) {
            InterfaceRefType interfaceRefXml = iFaceDeclrXml.getInterface().getInterfaceRef();

            // Location
            String interfaceLocationString = interfaceRefXml.getInterfaceLocation().trim();
            UUID iFaceLocationId = new UUID(interfaceLocationString);

            InterfaceRef reference = new InterfaceRef(iFaceLocationId);

            // Annotation
            setAnnotation(reference, interfaceRefXml);

            iFace = reference;
        }
        else if (iFaceDeclrXml.getInterface().isSetInterfaceSpec()) {
            InterfaceSpecType interfaceSpecXml = iFaceDeclrXml.getInterface().getInterfaceSpec();
            Specification specification = new Interface.Specification(interfaceSpecXml.getName());

            // Annotation
            setAnnotation(specification, interfaceSpecXml);

            // name
            String nameString = interfaceSpecXml.getName().trim();
            specification.setName(nameString);

            // Extended
            String[] extendedStrings = interfaceSpecXml.getExtendedArray();
            ID[] extendedIds = new ID[extendedStrings.length];
            for (int i = 0; i < extendedStrings.length; i++) {
                String extendedString = extendedStrings[i].trim();
                ID extendedId = new ID(extendedString);
                extendedIds[i] = extendedId;
            }
            specification.setExtended(extendedIds);

            // Operations
            InterfaceOperationType[] operationsXml = interfaceSpecXml.getOperationArray();
            Operation[] operations = new Operation[operationsXml.length];
            for (int i = 0; i < operations.length; i++) {
                InterfaceOperationType operationXml = operationsXml[i];
                operations[i] = parseInterfaceOperation(operationXml);
            }
            specification.setOperations(operations);

            iFace = specification;
        }
        else if (iFaceDeclrXml.getInterface().isSetInterfaceResourceType()) {
            String name = iFaceDeclrXml.getInterface().getInterfaceResourceType().getName().trim();
            iFace = new ResourceType(name);
            setAnnotation(iFace, iFaceDeclrXml.getInterface().getInterfaceResourceType());
        }

        // create the instance
        InterfaceDeclr interfaceDeclr = new InterfaceDeclr(id, providerRef, iFace);

        // Annotation
        this.setAnnotation(interfaceDeclr, iFaceDeclrXml);

        // set remaining attributes
        interfaceDeclr.setEndpoints(endpoints);
        interfaceDeclr.setProviderRef(providerRef);
        interfaceDeclr.setId(id);

        return interfaceDeclr;
    }

    public Operation parseInterfaceOperation(Object operationObject) throws Exception {
        assert operationObject != null : "The operation to be parsed is null!";
        InterfaceOperationType operationXml = (InterfaceOperationType) operationObject;
        // Name
        String nameString = operationXml.getName().trim();
        STND name = new STND(nameString);

        // create the instance
        Operation operation = new Operation(name);

        // Annotation
        setAnnotation(operation, operationXml);

        // Faults
        String[] faultsXml = operationXml.getFaultArray();
        STND[] faults = new STND[faultsXml.length];
        for (int i = 0; i < faultsXml.length; i++) {
            faults[i] = new STND(faultsXml[i].trim());
        }
        operation.setFaults(faults);

        // Input
        InterfaceOperationPropertyType[] inputsXml = operationXml.getInputArray();
        Interface.Operation.Property[] inputs = new Property[inputsXml.length];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = parseInterfaceOperationProperty(inputsXml[i]);
        }
        operation.setInputs(inputs);

        // Output
        InterfaceOperationPropertyType[] outputsXml = operationXml.getOutputArray();
        Interface.Operation.Property[] outputs = new Property[outputsXml.length];
        for (int i = 0; i < outputs.length; i++) {
            outputs[i] = parseInterfaceOperationProperty(outputsXml[i]);
        }
        operation.setOutputs(outputs);

        // Related
        InterfaceOperationPropertyType[] relatedXml = operationXml.getRelatedArray();
        Interface.Operation.Property[] related = new Property[relatedXml.length];
        for (int i = 0; i < related.length; i++) {
            related[i] = parseInterfaceOperationProperty(relatedXml[i]);
        }
        operation.setRelated(related);

        return operation;
    }

    public Property parseInterfaceOperationProperty(Object iFaceOperationPropertyObject) throws Exception {
        assert iFaceOperationPropertyObject != null : "The interface operation property to be parsed is null!";
        InterfaceOperationPropertyType iFaceOperationProperty =
                (InterfaceOperationPropertyType) iFaceOperationPropertyObject;
        // Name
        String nameString = iFaceOperationProperty.getName().trim();
        ID name = new ID(nameString);

        Property property = new Property(name);

        // Annotation
        setAnnotation(property, iFaceOperationProperty);

        // Auxiliary
        boolean auxiliary = iFaceOperationProperty.getAuxiliary();
        property.setAuxiliary(auxiliary);

        // Datatype/Unit
        // TODO what is datatype if none is set? null or ""?
        if (iFaceOperationProperty.isSetDatatype()) {
            String datatypeString = iFaceOperationProperty.getDatatype().trim();
            STND datatype = new STND(datatypeString);
            property.setDatatype(datatype);
        }

        // DomainExpr
        if (iFaceOperationProperty.isSetDomain()) {
            DomainExprType domainXml = iFaceOperationProperty.getDomain();
            DomainExpr domainExpr = parseDomainExpr(domainXml);
            property.setDomain(domainExpr);
        }

        return property;
    }

    public DomainExpr parseDomainExpr(Object domainExprObject) throws Exception {
        assert domainExprObject != null : "The domain expression to be parsed is null!";
        DomainExprType domainExprXml = (DomainExprType) domainExprObject;

        // Find out what it is: Simple or Compound
        if (domainExprXml.isSetSimpleDomainExpr()) {
            SimpleDomainExprType simpleDomainExprXml = domainExprXml.getSimpleDomainExpr();
            SimpleDomainExpr simpleDomainExpr = parseSimpleDomainExpr(simpleDomainExprXml);

            return simpleDomainExpr;
        }
        else {
            CompoundDomainExprType compoundDomainExprXml = domainExprXml.getCompoundDomainExpr();
            CompoundDomainExpr compoundDomainExpr = parseCompoundDomainExpr(compoundDomainExprXml);

            return compoundDomainExpr;
        }
    }

    public CompoundDomainExpr parseCompoundDomainExpr(Object compoundDomainExprObject) throws Exception {
        assert compoundDomainExprObject != null : "The compound domain expression to be parsed is null!";
        CompoundDomainExprType compoundDomainExprXml = (CompoundDomainExprType) compoundDomainExprObject;

        DomainExpr[] domainExpressions = null;

        // find out whether it is unary or binary
        String operatorString = null;
        operatorString = compoundDomainExprXml.getLogicalOp().trim();

        // Subexpressions
        DomainExprType[] binarySubexpressionsXml = compoundDomainExprXml.getSubexpressionArray();
        domainExpressions = new DomainExpr[binarySubexpressionsXml.length];
        for (int i = 0; i < binarySubexpressionsXml.length; i++) {
            domainExpressions[i] = parseDomainExpr(binarySubexpressionsXml[i]);
        }

        // create the operator instance
        STND operator = new STND(operatorString);

        // create the instance
        CompoundDomainExpr compoundDomainExpr = new CompoundDomainExpr(operator, domainExpressions);

        // set parsed values
        compoundDomainExpr.setSubExpressions(domainExpressions);
        compoundDomainExpr.setLogicalOp(operator);

        return compoundDomainExpr;
    }

    public SimpleDomainExpr parseSimpleDomainExpr(Object simpleDomainExprObject) throws Exception {
        assert simpleDomainExprObject != null : "The simple domain expression to be parsed is null!";
        SimpleDomainExprType simpleDomainExprXml = (SimpleDomainExprType) simpleDomainExprObject;
        // Operator
        String comparisonOpXml = simpleDomainExprXml.getComparisonOp().trim();
        STND comparisonOp = new STND(comparisonOpXml);

        // Value
        ValueExprType valueXml = simpleDomainExprXml.getValue();
        ValueExpr value = parseValueExpr(valueXml);

        // create the instance
        SimpleDomainExpr simpleDomainExpr = new SimpleDomainExpr(value, comparisonOp);

        return simpleDomainExpr;
    }

    public ValueExpr parseValueExpr(Object valueObject) throws Exception {
        assert valueObject != null : "The value expression to be parsed is null!";
        ValueExprType valueXml = (ValueExprType) valueObject;
        if (valueXml.isSetDomainExpr()) {
            Node domNode = valueXml.getDomainExpr().getDomNode();
            DomainExprDocument domainExprXml = DomainExprDocument.Factory.parse(domNode);
            DomainExpr domainExpr = parseDomainExpr(domainExprXml.getDomainExpr());
            return domainExpr;
        }
        else if (valueXml.isSetEventExpr()) {
            Node domNode = valueXml.getEventExpr().getDomNode();
            EventExprDocument eventExprXml = EventExprDocument.Factory.parse(domNode);
            EventExpr eventExpr = parseEventExpr(eventExprXml.getEventExpr());
            // setAnnotation(eventExpr, valueXml);
            return eventExpr;
        }
        else if (valueXml.isSetFuncExpr()) {
            Node domNode = valueXml.getFuncExpr().getDomNode();
            FuncExprDocument funcExprXml = FuncExprDocument.Factory.parse(domNode);
            FunctionalExpr functionalExpr = parseFunctionalExpr(funcExprXml.getFuncExpr());
            // setAnnotation(functionalExpr, valueXml);
            return functionalExpr;
        }
        else if (valueXml.isSetBOOL()) {
            boolean boolBool = valueXml.getBOOL();
            BOOL bool = new BOOL(boolBool);
            return bool;
        }
        else if (valueXml.isSetCONST()) {
            CONSTType constXml = valueXml.getCONST();
            CONST constConst = null;
            if (constXml.isSetDatatype()) {
                constConst = new CONST(constXml.getValue(), new STND(constXml.getDatatype()));
            }
            else {
                constConst = new CONST(constXml.getValue(), null);
            }
            return constConst;
        }
        else if (valueXml.isSetID()) {
            String idString = valueXml.getID().trim();
            ID id = new ID(idString);
            return id;
        }
        else if (valueXml.isSetTIME()) {
            Calendar timeCalendar = valueXml.getTIME();
            TIME time = new TIME(timeCalendar);
            return time;
        }
        else if (valueXml.isSetPATH()) {
            // String pathString= valueXml.getPATH();
            // PATH path = new PATH(pathString);
            // FIXME no PATH found in sla model
            return null;
        }
        else if (valueXml.isSetUUID()) {
            String uuid = valueXml.getUUID().trim();
            return new UUID(uuid);
        }
        else if (valueXml.isSetSTND()) {
            String stnd = valueXml.getSTND().trim();
            return new STND(stnd);
        }
        else if (valueXml.isSetListValueExpr()) {
            ListValueExprType valueListXml = valueXml.getListValueExpr();
            ValueExprType[] valuesXml = valueListXml.getValueArray();
            List<ValueExpr> valueExprs = new ArrayList<ValueExpr>(valuesXml.length);
            for (int i = 0; i < valuesXml.length; i++) {
                valueExprs.add(parseValueExpr(valuesXml[i]));
            }
            LIST valueExprList = new LIST();
            valueExprList.addAll(valueExprs);
            return valueExprList;
        }
        else if (valueXml.isSetServiceRef()) {
            return parseServiceRef((ServiceRefType) valueXml.getServiceRef());
        }
        else if (valueXml.isSetDomainExpr()) {
            return parseDomainExpr(valueXml.getDomainExpr());
        }
        else
            return null;
    }

    public ServiceRef parseServiceRef(Object serviceRef) {
        assert serviceRef != null : "The service reference to be parsed is null!";
        ServiceRefType serviceRefXml = (ServiceRefType) serviceRef;

        // Endpoint IDs
        ID[] endpointIds = new ID[0];
        if (serviceRefXml.isSetEndpointList()) {
            String[] endpointStrings = serviceRefXml.getEndpointList().getIDArray();
            endpointIds = new ID[endpointStrings.length];
            for (int i = 0; i < endpointStrings.length; i++) {
                endpointIds[i] = new ID(endpointStrings[i].trim());
            }
        }

        // InterfaceDeclrs
        ID[] interfaceIds = new ID[0];
        if (serviceRefXml.isSetInterfaceList()) {
            String[] interfaceStrings = serviceRefXml.getInterfaceList().getIDArray();
            interfaceIds = new ID[interfaceStrings.length];
            for (int i = 0; i < interfaceIds.length; i++) {
                interfaceIds[i] = new ID(interfaceStrings[i].trim());
            }
        }

        // Operation IDs
        ID[] operationIds = new ID[0];
        if (serviceRefXml.isSetOperationList()) {
            String[] operationStrings = serviceRefXml.getOperationList().getIDArray();
            operationIds = new ID[operationStrings.length];
            for (int i = 0; i < operationIds.length; i++) {
                operationIds[i] = new ID(operationStrings[i].trim());
            }
        }

        return new ServiceRef(interfaceIds, operationIds, endpointIds);
    }

    public EventExpr parseEventExpr(Object eventExprObject) throws Exception {
        assert eventExprObject != null : "The event expression to be parsed is null!";
        EventExprType eventExprXml = (EventExprType) eventExprObject;

        // Operator
        String operatorString = eventExprXml.getOperator().trim();
        STND operator = new STND(operatorString);

        // Parameters
        ExprType[] parametersXml = eventExprXml.getParameterArray();
        Expr[] parameters = new Expr[parametersXml.length];
        for (int i = 0; i < parameters.length; i++) {
            ExprType exprXml = parametersXml[i];
            parameters[i] = parseExpr(exprXml);
        }

        EventExpr eventExpr = new EventExpr(operator, parameters);
        // Annotation
        setAnnotation(eventExpr, eventExprXml);

        return eventExpr;
    }

    public Expr parseExpr(Object expr) throws Exception {
        assert expr != null : "The expression to be parsed is null!";
        ExprType exprXml = (ExprType) expr;
        if (exprXml.isSetConstraintExpr()) {
            return parseConstraintExpr(exprXml.getConstraintExpr());
        }
        else if (exprXml.isSetValueExpr()) {
            return parseValueExpr(exprXml.getValueExpr());
        }
        else {
            throw new Exception();
        }
    }

    public ConstraintExpr parseConstraintExpr(Object constraintObject) throws Exception {
        assert constraintObject != null : "The constraint expression to be parsed is null!";
        ConstraintExpr constraintExpr = null;
        ConstraintExprType constraintXml = (ConstraintExprType) constraintObject;

        // determine the type of constraint expression
        if (constraintXml.isSetTypeConstraintExpr()) {
            TypeConstraintExprType typeConstraintExprXml = constraintXml.getTypeConstraintExpr();

            // Error
            CONST errorConst = null;
            if (typeConstraintExprXml.isSetError()) {
                CONSTType errorXml = typeConstraintExprXml.getError();
                errorConst = new CONST(errorXml.getValue(), new STND(errorXml.getDatatype()));
            }

            // Domain
            DomainExprType domainExprXml = typeConstraintExprXml.getDomain();
            DomainExpr domainExpr = parseDomainExpr(domainExprXml);

            // Value
            ValueExprType valueXml = typeConstraintExprXml.getValue();
            ValueExpr valueExpr = parseValueExpr(valueXml);

            TypeConstraintExpr typeConstraintExpr = new TypeConstraintExpr(valueExpr, domainExpr);
            typeConstraintExpr.setError(errorConst);

            constraintExpr = typeConstraintExpr;
        }
        else if (constraintXml.isSetCompoundConstraintExpr()) {
            CompoundConstraintExprType compoundConstraintExprXml = constraintXml.getCompoundConstraintExpr();

            // find out type of constraint expr..
            STND operator = null;
            ConstraintExpr[] subExpressions = null;

            // Operator
            String binaryLogicalOpString = compoundConstraintExprXml.getLogicalOp();
            operator = new STND(binaryLogicalOpString);

            // Sub-expressions
            ConstraintExprType[] binarySubexpressionsXml = compoundConstraintExprXml.getSubexpressionArray();
            subExpressions = new ConstraintExpr[binarySubexpressionsXml.length];
            for (int i = 0; i < binarySubexpressionsXml.length; i++) {
                ConstraintExprType constraintExprXml = binarySubexpressionsXml[i];
                subExpressions[i] = parseConstraintExpr(constraintExprXml);
            }
            CompoundConstraintExpr compoundConstraintExpr = new CompoundConstraintExpr(operator, subExpressions);
            constraintExpr = compoundConstraintExpr;
        }
        else
            throw new Exception("This constraint expression is neither a type constraint nor a compound constraint.");

        return constraintExpr;
    }

    public FunctionalExpr parseFunctionalExpr(Object funcExprObject) throws Exception {
        assert funcExprObject != null : "The functional expression to be parsed is null!";
        FuncExprType funcExprXml = (FuncExprType) funcExprObject;

        // Operator: "I'm the operator with my pocket calculator."
        String operatorString = funcExprXml.getOperator();
        STND operator = new STND(operatorString);

        // Parameters
        ValueExprType[] parametersXml = funcExprXml.getParameterArray();
        ValueExpr[] parameters = new ValueExpr[parametersXml.length];
        for (int i = 0; i < parameters.length; i++) {
            ValueExprType valueExprXml = parametersXml[i];
            parameters[i] = parseValueExpr(valueExprXml);
        }

        // create the instance
        FunctionalExpr functionalExpr = new FunctionalExpr(operator, parameters);

        // Annotation
        // setAnnotation(functionalExpr, funcExprXml);

        return functionalExpr;
    }

    public VariableDeclr parseVariableDeclr(Object variableDeclrObject) throws Exception {
        assert variableDeclrObject != null : "The variable declaration to be parsed is null!";
        VariableDeclrType variableDeclrXml = (VariableDeclrType) variableDeclrObject;

        VariableDeclr variableDeclr = null;

        if (variableDeclrXml.isSetCustomisable()) {
            CustomisableType customisableXml = variableDeclrXml.getCustomisable();

            // Var
            String varString = customisableXml.getVar();
            ID var = new ID(varString);

            // Value
            CONSTType valueXml = customisableXml.getValue();
            CONST valueConst = null;
            if (valueXml.isSetDatatype()) {
                valueConst = new CONST(valueXml.getValue(), new STND(valueXml.getDatatype()));
            }
            else {
                valueConst = new CONST(valueXml.getValue(), null);
            }

            // Expr
            DomainExprType exprXml = customisableXml.getExpr();
            DomainExpr expr = parseDomainExpr(exprXml);

            // create new instance
            Customisable customisable = new Customisable(var, expr, valueConst);

            variableDeclr = customisable;
        }
        else {
            // Var
            String varString = variableDeclrXml.getVar();
            ID var = new ID(varString);

            // Expr
            ExprType exprXml = variableDeclrXml.getExpr();
            Expr expr = parseExpr(exprXml);

            // create the instance
            variableDeclr = new VariableDeclr(var, expr);
        }

        // Annotation
        setAnnotation(variableDeclr, variableDeclrXml);

        return variableDeclr;
    }
}

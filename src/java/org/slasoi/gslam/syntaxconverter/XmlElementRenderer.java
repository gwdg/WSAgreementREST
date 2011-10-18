package org.slasoi.gslam.syntaxconverter;

import java.util.Calendar;

import org.apache.xmlbeans.XmlCalendar;
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
import org.slasoi.slamodel.service.Interface.Operation;
import org.slasoi.slamodel.service.Interface.Operation.Property;
import org.slasoi.slamodel.service.ResourceType;
import org.slasoi.slamodel.service.ServiceRef;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.CustomAction;
import org.slasoi.slamodel.sla.Customisable;
import org.slasoi.slamodel.sla.Endpoint;
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.Guaranteed.Action;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.InterfaceRef;
import org.slasoi.slamodel.sla.Invocation;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.Party.Operative;
import org.slasoi.slamodel.sla.VariableDeclr;
import org.slasoi.slamodel.sla.business.ComponentProductOfferingPrice;
import org.slasoi.slamodel.sla.business.Penalty;
import org.slasoi.slamodel.sla.business.PriceModification;
import org.slasoi.slamodel.sla.business.Product;
import org.slasoi.slamodel.sla.business.ProductOfferingPrice;
import org.slasoi.slamodel.sla.business.Termination;
import org.slasoi.slamodel.sla.business.TerminationClause;

import eu.slaatsoi.slamodel.AgreementPartyType;
import eu.slaatsoi.slamodel.AgreementTermType;
import eu.slaatsoi.slamodel.AnnotatedDocument;
import eu.slaatsoi.slamodel.CONSTType;
import eu.slaatsoi.slamodel.ComponentProdOfferingPriceType;
import eu.slaatsoi.slamodel.CompoundConstraintExprType;
import eu.slaatsoi.slamodel.CompoundDomainExprType;
import eu.slaatsoi.slamodel.ConstraintExprType;
import eu.slaatsoi.slamodel.CustomisableType;
import eu.slaatsoi.slamodel.DomainExprType;
import eu.slaatsoi.slamodel.EndpointType;
import eu.slaatsoi.slamodel.EventExprType;
import eu.slaatsoi.slamodel.ExprType;
import eu.slaatsoi.slamodel.FuncExprType;
import eu.slaatsoi.slamodel.GuaranteedActionDefnType;
import eu.slaatsoi.slamodel.GuaranteedActionType;
import eu.slaatsoi.slamodel.GuaranteedStateType;
import eu.slaatsoi.slamodel.GuaranteedType;
import eu.slaatsoi.slamodel.IDListType;
import eu.slaatsoi.slamodel.IdValueExprEntryType;
import eu.slaatsoi.slamodel.InterfaceDeclrType;
import eu.slaatsoi.slamodel.InterfaceOperationPropertyType;
import eu.slaatsoi.slamodel.InterfaceOperationType;
import eu.slaatsoi.slamodel.InterfaceRefType;
import eu.slaatsoi.slamodel.InterfaceSpecType;
import eu.slaatsoi.slamodel.InterfaceType;
import eu.slaatsoi.slamodel.InvocationType;
import eu.slaatsoi.slamodel.MapIdValueExpr;
import eu.slaatsoi.slamodel.MapStndAny;
import eu.slaatsoi.slamodel.OperativeType;
import eu.slaatsoi.slamodel.PenaltyActionType;
import eu.slaatsoi.slamodel.PriceModificationType;
import eu.slaatsoi.slamodel.ProductOfferingPriceType;
import eu.slaatsoi.slamodel.ProductType;
import eu.slaatsoi.slamodel.ServiceRefType;
import eu.slaatsoi.slamodel.SimpleDomainExprType;
import eu.slaatsoi.slamodel.StndAnyEntryType;
import eu.slaatsoi.slamodel.TerminationActionType;
import eu.slaatsoi.slamodel.TerminationClauseType;
import eu.slaatsoi.slamodel.TypeConstraintExprType;
import eu.slaatsoi.slamodel.ValueExprType;
import eu.slaatsoi.slamodel.VariableDeclrType;

/**
 * Provides functionality to render the elements in the SLA model to XML.
 * @author Peter A. Chronz
 *
 */
public class XmlElementRenderer extends ElementRenderer {

    public AgreementTermType renderAgreementTerm(AgreementTerm agreementTermObject) throws Exception {
        assert agreementTermObject != null : "The agreement term to be rendered is null!";
        AgreementTerm agreementTerm = (AgreementTerm) agreementTermObject;
        AgreementTermType agreementTermXml = AgreementTermType.Factory.newInstance();

        // Annotation
        setAnnotation(agreementTerm, agreementTermXml);

        // ID
        agreementTermXml.setID(agreementTerm.getId().getValue());

        // Precondition
        ConstraintExpr precondition = agreementTerm.getPrecondition();
        if (precondition != null) {
            ConstraintExprType preconditionXml = renderConstraintExpr(precondition);
            agreementTermXml.setPrecondition(preconditionXml);
        }

        // VariableDeclr
        VariableDeclr[] variableDeclrs = agreementTerm.getVariableDeclrs();
        if (variableDeclrs != null) {
            VariableDeclrType[] variableDeclrsXml = new VariableDeclrType[variableDeclrs.length];
            for (int i = 0; i < variableDeclrs.length; i++) {
                VariableDeclr variableDeclr = variableDeclrs[i];
                variableDeclrsXml[i] = renderVariableDeclr(variableDeclr);
            }
            agreementTermXml.setVariableDeclrArray(variableDeclrsXml);
        }

        // Guaranteed
        Guaranteed[] guarantees = agreementTerm.getGuarantees();
        GuaranteedType[] guaranteesXml = new GuaranteedType[guarantees.length];
        for (int i = 0; i < guaranteesXml.length; i++) {
            Guaranteed guaranteed = guarantees[i];
            guaranteesXml[i] = renderGuaranteed(guaranteed);
        }
        agreementTermXml.setGuaranteedArray(guaranteesXml);

        return agreementTermXml;
    }

    public GuaranteedType renderGuaranteed(Guaranteed guaranteedObject) throws Exception {
        assert guaranteedObject != null : "The  to be rendered is null!";
        Guaranteed guaranteed = (Guaranteed) guaranteedObject;
        GuaranteedType guaranteedXml = GuaranteedType.Factory.newInstance();

        // Annotation
        setAnnotation(guaranteed, guaranteedXml);

        // Determine the type of guarantee
        if (guaranteed instanceof Guaranteed.State) {
            GuaranteedStateType stateXml = renderGuaranteedState((Guaranteed.State) guaranteed);
            guaranteedXml.setState(stateXml);
        }
        else if (guaranteed instanceof Guaranteed.Action) {
            GuaranteedActionType action = renderGuaranteedAction((Guaranteed.Action) guaranteed);
            guaranteedXml.setAction(action);
        }

        return guaranteedXml;
    }

    public GuaranteedActionType renderGuaranteedAction(Guaranteed.Action guaranteedActionObject) throws Exception {
        assert guaranteedActionObject != null : "The guaranteed action to be rendered is null!";
        Guaranteed.Action guaranteedAction = (Guaranteed.Action) guaranteedActionObject;
        GuaranteedActionType guaranteedActionXml = GuaranteedActionType.Factory.newInstance();

        // ID
        guaranteedActionXml.setID(guaranteedAction.getId().getValue());

        // ActorRef
        guaranteedActionXml.setActorRef(guaranteedAction.getActorRef().getValue());

        // Policy
        guaranteedActionXml.setPolicy(guaranteedAction.getPolicy().getValue());

        // Precondition
        EventExprType preconditionXml = renderEventExpr(guaranteedAction.getPrecondition());
        guaranteedActionXml.setPrecondition(preconditionXml);

        // Postcondition
        GuaranteedActionDefnType guaranteedActionDefnType =
                renderGuaranteedActionDefn(guaranteedAction.getPostcondition());
        guaranteedActionXml.setPostcondition(guaranteedActionDefnType);

        return guaranteedActionXml;
    }

    public GuaranteedActionDefnType renderGuaranteedActionDefn(Action.Defn actionDefnObject) throws Exception {
        assert actionDefnObject != null : "The action definition to be rendered is null!";
        Guaranteed.Action.Defn actionDefn = (Guaranteed.Action.Defn) actionDefnObject;
        GuaranteedActionDefnType guaranteedActionDefnXml = GuaranteedActionDefnType.Factory.newInstance();

        if (actionDefn instanceof Invocation) {
            InvocationType invocationXml = renderInvocation((Invocation) actionDefn);
            guaranteedActionDefnXml.setInvocation(invocationXml);
            setAnnotation(actionDefn, guaranteedActionDefnXml);
        }
        else if (actionDefn instanceof CustomAction) {
            guaranteedActionDefnXml.addNewCustomAction();
            setAnnotation(actionDefn, guaranteedActionDefnXml.getCustomAction());
        }
        else if (actionDefn instanceof Termination) {
            TerminationActionType terminationXml = renderTermination((Termination) actionDefn);
            guaranteedActionDefnXml.setTerminationAction(terminationXml);
            setAnnotation(actionDefn, guaranteedActionDefnXml);
        }
        else if (actionDefn instanceof ProductOfferingPrice) {
            ProductOfferingPriceType pop = renderProductOfferingPrice((ProductOfferingPrice) actionDefn);
            guaranteedActionDefnXml.setProductOfferingPrice(pop);
            setAnnotation(actionDefn, guaranteedActionDefnXml);
        }
        else if (actionDefn instanceof TerminationClause) {
            TerminationClauseType terminationClause = renderTerminationClause((TerminationClause) actionDefn);
            guaranteedActionDefnXml.setTerminationClause(terminationClause);
            setAnnotation(actionDefn, guaranteedActionDefnXml);
        }
        else if (actionDefn instanceof Penalty) {
            PenaltyActionType penalty = renderPenalty((Penalty) actionDefn);
            guaranteedActionDefnXml.setPenaltyAction(penalty);
            setAnnotation(actionDefn, guaranteedActionDefnXml);
        }
        
        

        return guaranteedActionDefnXml;
    }

    public PenaltyActionType renderPenalty(Penalty actionDefn) {
        assert actionDefn != null : "The penalty action to be rendered is null!";
        CONST price = actionDefn.getPrice();

        PenaltyActionType penalty = PenaltyActionType.Factory.newInstance();
        STND priceType = price.getDatatype();
        if (priceType != null) 
        	penalty.addNewPrice().setDatatype(priceType.getValue());
        else
        	penalty.addNewPrice().setDatatype(null);
        penalty.getPrice().setValue(price.getValue());

        return penalty;
    }

    public TerminationClauseType renderTerminationClause(TerminationClause actionDefn) {
        assert actionDefn != null : "The termination clause to be rendered is null!";
        TerminationClauseType terminationClause = TerminationClauseType.Factory.newInstance();

        terminationClause.addNewFees().setDatatype(actionDefn.getFees().getDatatype().getValue());
        terminationClause.getFees().setValue(actionDefn.getFees().getValue());

        terminationClause.addNewNotificationPeriod().setDatatype(
                actionDefn.getNotificationPeriod().getDatatype().getValue());
        terminationClause.getNotificationPeriod().setValue(actionDefn.getNotificationPeriod().getValue());

        terminationClause.setNotificationMethod(actionDefn.getNotificationMethod().getValue());

        terminationClause.setID(actionDefn.getId().getValue());

        terminationClause.setTerminationInitiator(actionDefn.getInitiator().getValue());

        terminationClause.setTerminationClauseType(actionDefn.getType());

        terminationClause.setTerminationClauseDescription(actionDefn.getClause());

        return terminationClause;
    }

    public ProductOfferingPriceType renderProductOfferingPrice(ProductOfferingPrice actionDefn) {
        assert actionDefn != null : "The product offering price to be rendered is null!";
        ProductOfferingPriceType pop = ProductOfferingPriceType.Factory.newInstance();

        pop.setBillingFrequency(actionDefn.getBillingFrequency().getValue());

        pop.setID(actionDefn.getId().getValue());

        pop.setName(actionDefn.getName());

        ComponentProductOfferingPrice[] prices = actionDefn.getComponentProductOfferingPrices();
        ComponentProdOfferingPriceType[] pricesXml = new ComponentProdOfferingPriceType[prices.length];
        for (int i = 0; i < pricesXml.length; i++) {
            pricesXml[i] = renderComponentProductOfferingPrice(prices[i]);
        }
        pop.setComponentProdOfferingPriceArray(pricesXml);
        if (actionDefn.getProduct() != null)
            pop.setProduct(renderProduct(actionDefn.getProduct()));

        pop.setValidFrom(actionDefn.getValidFrom().getValue());
        pop.setValidUntil(actionDefn.getValidUntil().getValue());
        
        if(actionDefn.getDescr() != null)
            pop.setDescription(actionDefn.getDescr());
        else
            pop.setDescription("");

        return pop;
    }

    public ProductType renderProduct(Product product) {
        assert product != null : "The product to be rendered is null!";
        ProductType productXml = ProductType.Factory.newInstance();

        productXml.setID(product.getId().getValue());

        productXml.setName(product.getName());

        // Annotation
        setAnnotation(product, productXml);

        return productXml;
    }

    public ComponentProdOfferingPriceType renderComponentProductOfferingPrice(ComponentProductOfferingPrice price) {
        assert price != null : "The component offering price to be rendered is null!";
        ComponentProdOfferingPriceType priceXml = ComponentProdOfferingPriceType.Factory.newInstance();

        priceXml.setID(price.getId().getValue());

        CONSTType priceXML = CONSTType.Factory.newInstance();
        STND priceDataType = price.getPrice().getDatatype(); 
        if (priceDataType != null) 
        	priceXML.setDatatype(priceDataType.getValue());
        priceXML.setValue(price.getPrice().getValue());
        priceXml.setPrice(priceXML);

        PriceModification[] priceModifications = price.getPriceModifications();
        PriceModificationType[] priceModificationsXml = new PriceModificationType[priceModifications.length];
        for (int i = 0; i < priceModificationsXml.length; i++) {
            priceModificationsXml[i] = renderPriceModification(priceModifications[i]);
        }
        priceXml.setPriceModificationArray(priceModificationsXml);

        priceXml.setPriceType(price.getPriceType().getValue());

        CONSTType quantity = CONSTType.Factory.newInstance();
        quantity.setValue(price.getQuantity().getValue());
        if(price.getQuantity().getDatatype() != null)
            quantity.setDatatype(price.getQuantity().getDatatype().getValue());
        priceXml.setQuantity(quantity);

        return priceXml;
    }

    public PriceModificationType renderPriceModification(PriceModification priceModification) {
        assert priceModification != null : "The price modification to be rendered is null!";
        PriceModificationType priceModificationXml = PriceModificationType.Factory.newInstance();

        priceModificationXml.setType(priceModification.getType().getValue());

        CONSTType value = priceModificationXml.addNewValue();
        value.setDatatype(priceModification.getValue().getDatatype().getValue());
        value.setValue(priceModification.getValue().getValue());

        return priceModificationXml;
    }

    public TerminationActionType renderTermination(Termination actionDefn) {
        assert actionDefn != null : "The termination action to be rendered is null!";
        TerminationActionType terminationAction = TerminationActionType.Factory.newInstance();

        terminationAction.setName(actionDefn.getName());

        terminationAction.setTerminationClauseID(actionDefn.getTerminationClauseId().getValue());

        return terminationAction;
    }

    public InvocationType renderInvocation(Invocation invocationObject) throws Exception {
        assert invocationObject != null : "The invocation to be rendered is null!";
        Invocation invocation = (Invocation) invocationObject;
        InvocationType invocationXml = InvocationType.Factory.newInstance();

        // Endpoint
        invocationXml.setEndpoint(invocation.getEndpointId().getValue());

        // Operation
        invocationXml.setOperation(invocation.getOperationId().getValue());

        // Parameters
        ID[] parameterKeys = invocation.getParameterKeys();
        MapIdValueExpr parametersXml = invocationXml.addNewParameters();
        for (int i = 0; i < parameterKeys.length; i++) {
            ID key = parameterKeys[i];
            ValueExpr parameter = invocation.getParameterValue(key);
            IdValueExprEntryType entryXml = parametersXml.addNewEntry();

            // Key
            entryXml.setKey(key.getValue());

            // Value
            ValueExprType parameterXml = renderValueExpr(parameter);
            entryXml.setValue(parameterXml);
        }

        return invocationXml;
    }

    public ValueExprType renderValueExpr(ValueExpr valueExprObject) throws Exception {
        assert valueExprObject != null : "The value expression to be rendered is null!";
        ValueExpr valueExpr = (ValueExpr) valueExprObject;
        ValueExprType valueExprXml = ValueExprType.Factory.newInstance();

        if (valueExpr instanceof DomainExpr) {
            DomainExprType domainExprXml = renderDomainExpr((DomainExpr) valueExpr);
            valueExprXml.setDomainExpr(domainExprXml);
        }
        else if (valueExpr instanceof EventExpr) {
            EventExprType eventExprXml = renderEventExpr((EventExpr) valueExpr);
            valueExprXml.setEventExpr(eventExprXml);
        }
        else if (valueExpr instanceof FunctionalExpr) {
            FuncExprType funcExprXml = renderFuncExpr((FunctionalExpr) valueExpr);
            valueExprXml.setFuncExpr(funcExprXml);
        }
        else if (valueExpr instanceof BOOL) {
            valueExprXml.setBOOL(((BOOL) valueExpr).getValue());
        }
        else if (valueExpr instanceof CONST) {
            CONST constValue = (CONST) valueExpr;
            CONSTType constXml = CONSTType.Factory.newInstance();
            constXml.setValue(constValue.getValue());
            if (constValue.getDatatype() != null)
                constXml.setDatatype(constValue.getDatatype().getValue());
            valueExprXml.setCONST(constXml);
        }
        else if (valueExpr instanceof ID) {
            valueExprXml.setID(((ID) valueExpr).getValue());
        }
        else if (valueExpr instanceof TIME) {
            Calendar dateTimeCal = ((TIME) valueExpr).getValue();
            Calendar calendar = new XmlCalendar(dateTimeCal.getTime());
            valueExprXml.setTIME(calendar);
        }
        // else if(valueExpr instanceof PATH) {
        // // TODO find PATH
        // }
        else if (valueExpr instanceof UUID) {
            String value = ((UUID) valueExpr).getValue();
            valueExprXml.setUUID(value);
        }
        else if (valueExpr instanceof STND) {
            String value = ((STND) valueExpr).getValue();
            valueExprXml.setSTND(value);
        }
        else if (valueExpr instanceof ServiceRef) {
            ServiceRefType serviceRefXml = renderServiceRef((ServiceRef) valueExpr);
            valueExprXml.setServiceRef(serviceRefXml);
        }
        else if (valueExpr instanceof LIST) {
            ValueExprType[] valuesXml = new ValueExprType[((LIST) valueExpr).size()];
            for (int i = 0; i < ((LIST) valueExpr).size(); i++) {
                valuesXml[i] = renderValueExpr(((LIST) valueExpr).get(i));
            }
            valueExprXml.addNewListValueExpr().setValueArray(valuesXml);
        }
        else if (valueExpr instanceof DomainExpr) {
            valueExprXml.setDomainExpr(renderDomainExpr((DomainExpr) valueExpr));
        }

        return valueExprXml;
    }

    public ServiceRefType renderServiceRef(ServiceRef serviceRef) {
        assert serviceRef != null : "The service reference to be rendered is null!";
        ServiceRefType serviceRefXml = ServiceRefType.Factory.newInstance();

        // Endpoint IDs
        ID[] endpointIds = serviceRef.getEndpointIds();
        IDListType endpointsXml = serviceRefXml.addNewEndpointList();
        for (int i = 0; i < endpointIds.length; i++) {
            endpointsXml.addID(endpointIds[i].getValue());
        }

        // Interface Declarations
        ID[] interfaceDeclrIds = serviceRef.getInterfaceDeclrIds();
        IDListType interfacesXml = serviceRefXml.addNewInterfaceList();
        for (int i = 0; i < interfaceDeclrIds.length; i++) {
            interfacesXml.addID(interfaceDeclrIds[i].getValue());
        }

        // Operation IDs
        ID[] operationIds = serviceRef.getOperationIds();
        IDListType operationsXml = serviceRefXml.addNewOperationList();
        for (int i = 0; i < operationIds.length; i++) {
            operationsXml.addID(operationIds[i].getValue());
        }

        return serviceRefXml;
    }

    public FuncExprType renderFuncExpr(FunctionalExpr funcExprObject) throws Exception {
        assert funcExprObject != null : "The functional expression to be rendered is null!";
        FunctionalExpr funcExpr = (FunctionalExpr) funcExprObject;
        FuncExprType funcExprXml = FuncExprType.Factory.newInstance();

        // Annotation
        setAnnotation(funcExpr, funcExprXml);

        // Operator
        funcExprXml.setOperator(funcExpr.getOperator().getValue());

        // Parameter
        ValueExpr[] parameters = funcExpr.getParameters();
        ValueExprType[] parametersXml = new ValueExprType[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parametersXml[i] = renderValueExpr(parameters[i]);
        }
        funcExprXml.setParameterArray(parametersXml);

        return funcExprXml;
    }

    public DomainExprType renderDomainExpr(DomainExpr domainExprObject) throws Exception {
        assert domainExprObject != null : "The domain expression to be rendered is null!";
        DomainExpr domainExpr = (DomainExpr) domainExprObject;
        DomainExprType domainExprXml = DomainExprType.Factory.newInstance();

        // identify the type of DomainExpr
        if (domainExpr instanceof SimpleDomainExpr) {
            SimpleDomainExprType simpleDomainExprXml = renderSimpleDomainExpr((SimpleDomainExpr) domainExpr);
            domainExprXml.setSimpleDomainExpr(simpleDomainExprXml);
        }
        else if (domainExpr instanceof CompoundDomainExpr) {
            CompoundDomainExprType compoundDomainExprXml = renderCompoundDomainExpr((CompoundDomainExpr) domainExpr);
            domainExprXml.setCompoundDomainExpr(compoundDomainExprXml);
        }

        return domainExprXml;
    }

    public CompoundDomainExprType renderCompoundDomainExpr(CompoundDomainExpr compoundDomainExprObject)
            throws Exception {
        assert compoundDomainExprObject != null : "The compound domain expression to be rendered is null!";
        CompoundDomainExpr compoundDomainExpr = (CompoundDomainExpr) compoundDomainExprObject;
        CompoundDomainExprType compoundDomainExprXml = CompoundDomainExprType.Factory.newInstance();

        // Subexpressions
        DomainExpr[] subExpressions = compoundDomainExpr.getSubExpressions();
        DomainExprType[] subExpressionsXml = new DomainExprType[subExpressions.length];
        for (int i = 0; i < subExpressions.length; i++) {
            DomainExprType domainExprXml = renderDomainExpr(subExpressions[i]);
            subExpressionsXml[i] = domainExprXml;
        }
        compoundDomainExprXml.setSubexpressionArray(subExpressionsXml);

        // Logical Operator
        STND logicalOp = compoundDomainExpr.getLogicalOp();
        compoundDomainExprXml.setLogicalOp(logicalOp.getValue());

        return compoundDomainExprXml;
    }

    public SimpleDomainExprType renderSimpleDomainExpr(SimpleDomainExpr simpleDomainExprObject) throws Exception {
        assert simpleDomainExprObject != null : "The simple domain expression to be rendered is null!";
        SimpleDomainExpr simpleDomainExpr = (SimpleDomainExpr) simpleDomainExprObject;
        SimpleDomainExprType simpleDomainExprXml = SimpleDomainExprType.Factory.newInstance();

        // Value
        ValueExpr value = simpleDomainExpr.getValue();
        ValueExprType valueExprXml = renderValueExpr(value);
        simpleDomainExprXml.setValue(valueExprXml);

        // Comparison Operator
        STND comparisonOp = simpleDomainExpr.getComparisonOp();
        simpleDomainExprXml.setComparisonOp(comparisonOp.getValue());

        return simpleDomainExprXml;
    }

    public EventExprType renderEventExpr(EventExpr eventExprObject) throws Exception {
        assert eventExprObject != null : "The event expression to be rendered is null!";
        EventExpr eventExpr = (EventExpr) eventExprObject;
        EventExprType eventExprXml = EventExprType.Factory.newInstance();

        // Annotation
        setAnnotation(eventExpr, eventExprXml);

        // Operator
        STND operator = eventExpr.getOperator();
        eventExprXml.setOperator(operator.getValue());

        // Parameter
        Expr[] parameters = eventExpr.getParameters();
        ExprType[] parametersXml = new ExprType[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Expr parameter = parameters[i];
            ExprType parameterXml = (ExprType) renderExpr(parameter);
            parametersXml[i] = parameterXml;
        }
        eventExprXml.setParameterArray(parametersXml);

        return eventExprXml;
    }

    public Object renderExpr(Expr expr) throws Exception {
        assert expr != null : "The expression to be rendered is null!";
        ExprType exprXml = ExprType.Factory.newInstance();
        if (expr instanceof ValueExpr) {
            exprXml.setValueExpr(renderValueExpr((ValueExpr) expr));
        }
        else if (expr instanceof ConstraintExpr) {
            exprXml.setConstraintExpr(renderConstraintExpr((ConstraintExpr) expr));
        }
        else {
            throw new Exception();
        }
        return exprXml;
    }

    public GuaranteedStateType renderGuaranteedState(Guaranteed.State guaranteedStateObject) throws Exception {
        assert guaranteedStateObject != null : "The guaranteed state to be rendered is null!";
        Guaranteed.State guaranteedState = (Guaranteed.State) guaranteedStateObject;
        GuaranteedStateType guaranteedStateXml = GuaranteedStateType.Factory.newInstance();

        // ID
        guaranteedStateXml.setID(guaranteedState.getId().getValue());

        // Priority
        CONSTType constXml = CONSTType.Factory.newInstance();
        CONST priority = guaranteedState.getPriority();
        if (priority != null) {
            constXml.setValue(priority.getValue());
            constXml.setDatatype(guaranteedState.getPriority().getDatatype().getValue());
            guaranteedStateXml.setPriority(constXml);
        }
        else
            guaranteedStateXml.setNilPriority();

        // State
        ConstraintExpr state = guaranteedState.getState();
        ConstraintExprType stateXml = renderConstraintExpr(state);
        guaranteedStateXml.setConstraint(stateXml);

        // Precondition
        if (guaranteedState.getPrecondition() != null) {
            ConstraintExpr precondition = guaranteedState.getPrecondition();
            ConstraintExprType constraintExprXml = renderConstraintExpr(precondition);
            guaranteedStateXml.setPrecondition(constraintExprXml);
        }

        return guaranteedStateXml;
    }

    public VariableDeclrType renderVariableDeclr(VariableDeclr variableDeclrObject) throws Exception {
        assert variableDeclrObject != null : "The variable declaration to be rendered is null!";
        VariableDeclr variableDeclr = (VariableDeclr) variableDeclrObject;
        VariableDeclrType variableDeclrXml = VariableDeclrType.Factory.newInstance();

        // Annotation
        setAnnotation(variableDeclr, variableDeclrXml);

        if (variableDeclr instanceof Customisable) {
            Customisable customisable = (Customisable) variableDeclr;

            // Value
            CustomisableType customisableXml = variableDeclrXml.addNewCustomisable();
            CONSTType constXml = CONSTType.Factory.newInstance();
            constXml.setValue(customisable.getValue().getValue());
            if (customisable.getValue().getDatatype() != null)
                constXml.setDatatype(customisable.getValue().getDatatype().getValue());
            customisableXml.setValue(constXml);

            // Expr
            Expr expr = customisable.getExpr();
            DomainExprType domainExprXml = renderDomainExpr((DomainExpr) expr);
            customisableXml.setExpr(domainExprXml);

            // Var
            ID var = customisable.getVar();
            customisableXml.setVar(var.getValue());
        }
        else {
            Expr expr = variableDeclr.getExpr();
            ExprType exprXml = (ExprType) renderExpr(expr);
            variableDeclrXml.setExpr(exprXml);

            // Var
            ID var = variableDeclr.getVar();
            variableDeclrXml.setVar(var.getValue());
        }

        return variableDeclrXml;
    }

    public ConstraintExprType renderConstraintExpr(ConstraintExpr constraintExprObject) throws Exception {
        assert constraintExprObject != null : "The constraint expression to be rendered is null!";
        ConstraintExpr constraintExpr = (ConstraintExpr) constraintExprObject;
        ConstraintExprType constraintExprXml = ConstraintExprType.Factory.newInstance();

        // identify the type
        if (constraintExpr instanceof CompoundConstraintExpr) {
            CompoundConstraintExprType compoundConstraintExprXml =
                    renderCompoundConstraintExpr((CompoundConstraintExpr) constraintExpr);
            constraintExprXml.setCompoundConstraintExpr(compoundConstraintExprXml);
        }
        else if (constraintExpr instanceof TypeConstraintExpr) {
            TypeConstraintExprType typeConstraintExprXml =
                    renderTypeConstraintExpr((TypeConstraintExpr) constraintExpr);
            constraintExprXml.setTypeConstraintExpr(typeConstraintExprXml);
        }

        return constraintExprXml;
    }

    public TypeConstraintExprType renderTypeConstraintExpr(TypeConstraintExpr typeConstraintExprObject)
            throws Exception {
        assert typeConstraintExprObject != null : "The type constraint expression to be rendered is null!";
        TypeConstraintExpr typeConstraintExpr = (TypeConstraintExpr) typeConstraintExprObject;
        TypeConstraintExprType typeConstraintExprXml = TypeConstraintExprType.Factory.newInstance();

        // Error
        CONST error = typeConstraintExpr.getError();
        if (error != null) {
            CONSTType errorXml = CONSTType.Factory.newInstance();
            errorXml.setValue(error.getValue());
            errorXml.setDatatype(error.getDatatype().getValue());
            typeConstraintExprXml.setError(errorXml);
        }

        // Domain
        DomainExpr domainExpr = typeConstraintExpr.getDomain();
        DomainExprType domainExprXml = renderDomainExpr(domainExpr);
        typeConstraintExprXml.setDomain(domainExprXml);

        // Value
        ValueExpr valueExpr = typeConstraintExpr.getValue();
        ValueExprType vaueExprXml = renderValueExpr(valueExpr);
        typeConstraintExprXml.setValue(vaueExprXml);

        return typeConstraintExprXml;
    }

    public CompoundConstraintExprType renderCompoundConstraintExpr(CompoundConstraintExpr compoundConstraintExprObject)
            throws Exception {
        assert compoundConstraintExprObject != null : "The compound constraint expression to be rendered is null!";
        CompoundConstraintExpr compoundConstraintExpr = (CompoundConstraintExpr) compoundConstraintExprObject;
        CompoundConstraintExprType compoundConstraintExprXml = CompoundConstraintExprType.Factory.newInstance();

        // Logical Operator
        STND logicalOp = compoundConstraintExpr.getLogicalOp();
        compoundConstraintExprXml.setLogicalOp(logicalOp.getValue());

        // Sub-Expressions
        ConstraintExpr[] subExpressions = compoundConstraintExpr.getSubExpressions();
        ConstraintExprType[] subExpressionsXml = new ConstraintExprType[subExpressions.length];
        for (int i = 0; i < subExpressions.length; i++) {
            ConstraintExpr subExpression = subExpressions[i];
            subExpressionsXml[i] = renderConstraintExpr(subExpression);
        }
        compoundConstraintExprXml.setSubexpressionArray(subExpressionsXml);

        return compoundConstraintExprXml;
    }

    public InterfaceDeclrType renderInterfaceDeclr(InterfaceDeclr interfaceDeclrObject) throws Exception {
        assert interfaceDeclrObject != null : "The interface declaration to be rendered is null!";
        InterfaceDeclr interfaceDeclr = (InterfaceDeclr) interfaceDeclrObject;
        InterfaceDeclrType iFaceDeclrXml = InterfaceDeclrType.Factory.newInstance();

        // Annotation
        setAnnotation(interfaceDeclr, iFaceDeclrXml);

        // ID
        ID id = interfaceDeclr.getId();
        iFaceDeclrXml.setID(id.getValue());

        // ProviderRef
        ID providerRef = interfaceDeclr.getProviderRef();
        iFaceDeclrXml.setProviderRef(providerRef.getValue());

        // Endpoint
        Endpoint[] endpoints = interfaceDeclr.getEndpoints();
        EndpointType[] endpointsXml = new EndpointType[endpoints.length];
        for (int i = 0; i < endpoints.length; i++) {
            Endpoint endpoint = endpoints[i];
            endpointsXml[i] = renderEndpoint(endpoint);
        }
        iFaceDeclrXml.setEndpointArray(endpointsXml);

        // Interface
        Interface interfaceDesc = interfaceDeclr.getInterface();
        InterfaceType interfaceXml = iFaceDeclrXml.addNewInterface();
        if (interfaceDesc instanceof Interface.Specification) {
            InterfaceSpecType iFaceSpecXml = renderInterfaceSpecification((Interface.Specification) interfaceDesc);
            interfaceXml.setInterfaceSpec(iFaceSpecXml);
        }
        else if (interfaceDesc instanceof InterfaceRef) {
            InterfaceRefType iFaceRefXml = renderInterfaceRef((InterfaceRef) interfaceDesc);
            interfaceXml.setInterfaceRef(iFaceRefXml);
        }
        else if (interfaceDesc instanceof ResourceType) {
            setAnnotation(interfaceDesc, interfaceXml.addNewInterfaceResourceType());
            interfaceXml.getInterfaceResourceType().setName(((ResourceType) interfaceDesc).getName());
        }

        return iFaceDeclrXml;
    }

    public InterfaceRefType renderInterfaceRef(InterfaceRef interfaceRefObject) {
        assert interfaceRefObject != null : "The interface reference to be rendered is null!";
        InterfaceRef interfaceRef = (InterfaceRef) interfaceRefObject;
        InterfaceRefType interfaceRefXml = InterfaceRefType.Factory.newInstance();

        // Location
        UUID interfaceLocation = interfaceRef.getInterfaceLocation();
        interfaceRefXml.setInterfaceLocation(interfaceLocation.getValue());

        return interfaceRefXml;
    }

    public InterfaceSpecType renderInterfaceSpecification(Interface.Specification interfaceSpecObject) throws Exception {
        assert interfaceSpecObject != null : "The interface specification to be rendered is null!";
        Interface.Specification interfaceSpec = (Interface.Specification) interfaceSpecObject;
        InterfaceSpecType interfaceSpecXml = InterfaceSpecType.Factory.newInstance();

        // Annotation
        setAnnotation(interfaceSpec, interfaceSpecXml);

        // Name
        interfaceSpecXml.setName(interfaceSpec.getName());

        // Extended
        ID[] extended = interfaceSpec.getExtended();
        if (extended != null) {
        	String[] extendedXml = new String[extended.length];        
	        for (int i = 0; i < extended.length; i++) {
	            extendedXml[i] = extended[i].getValue();
	        }
	        interfaceSpecXml.setExtendedArray(extendedXml);
        }

        // Operation
        Operation[] operations = interfaceSpec.getOperations();
        InterfaceOperationType[] operationsXml = new InterfaceOperationType[operations.length];
        for (int i = 0; i < operations.length; i++) {
            operationsXml[i] = renderInterfaceOperation(operations[i]);
        }
        interfaceSpecXml.setOperationArray(operationsXml);

        return interfaceSpecXml;
    }

    public InterfaceOperationType renderInterfaceOperation(Operation operationObject) throws Exception {
        assert operationObject != null : "The operation to be rendered is null!";
        Operation operation = (Operation) operationObject;
        InterfaceOperationType operationXml = InterfaceOperationType.Factory.newInstance();

        // Annotation
        setAnnotation(operation, operationXml);

        // Name
        operationXml.setName(operation.getName().getValue());

        // Fault
        STND[] faults = operation.getFaults();
        if (faults != null) {
            String[] faultsXml = new String[faults.length];
            for (int i = 0; i < faults.length; i++) {
                faultsXml[i] = faults[i].getValue();
            }
            operationXml.setFaultArray(faultsXml);
        }

        // Inputs
        Property[] inputs = operation.getInputs();
        if (inputs != null) {
            InterfaceOperationPropertyType[] inputsXml = new InterfaceOperationPropertyType[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                inputsXml[i] = renderInterfaceOperationProperty(inputs[i]);
            }
            operationXml.setInputArray(inputsXml);
        }

        // Outputs
        Property[] outputs = operation.getOutputs();
        if (outputs != null) {
            InterfaceOperationPropertyType[] outputsXml = new InterfaceOperationPropertyType[outputs.length];
            for (int i = 0; i < outputs.length; i++) {
                outputsXml[i] = renderInterfaceOperationProperty(outputs[i]);
            }
            operationXml.setOutputArray(outputsXml);
        }

        Property[] related = operation.getRelated();
        if (related != null) {
            InterfaceOperationPropertyType[] relatedXml = new InterfaceOperationPropertyType[related.length];
            for (int i = 0; i < related.length; i++) {
                relatedXml[i] = renderInterfaceOperationProperty(related[i]);
            }
            operationXml.setRelatedArray(relatedXml);
        }

        return operationXml;
    }

    public InterfaceOperationPropertyType renderInterfaceOperationProperty(Property propertyObject) throws Exception {
        assert propertyObject != null : "The interface operation property to be rendered is null!";
        Property property = (Property) propertyObject;
        InterfaceOperationPropertyType propertyXml = InterfaceOperationPropertyType.Factory.newInstance();

        // Annotation
        setAnnotation(property, propertyXml);

        // Name
        propertyXml.setName(property.getName().getValue());

        // Auxiliary
        propertyXml.setAuxiliary(property.isAuxiliary());

        // Datatype/Unit
        propertyXml.setDatatype(property.getDatatype().getValue());

        // Domain
        DomainExpr domainExpr = property.getDomain();
        if(domainExpr != null) {
            DomainExprType domainExprXml = renderDomainExpr(domainExpr);
            propertyXml.setDomain(domainExprXml);
        }

        return propertyXml;
    }

    public EndpointType renderEndpoint(Endpoint endpointObject) {
        assert endpointObject != null : "The endpoint to be rendered is null!";
        Endpoint endpoint = (Endpoint) endpointObject;
        EndpointType factoryXml = EndpointType.Factory.newInstance();

        // Annotation
        setAnnotation(endpoint, factoryXml);

        // ID
        factoryXml.setID(endpoint.getId().getValue());

        // Location
        if (endpoint.getLocation() != null)
        	factoryXml.setLocation(endpoint.getLocation().getValue());

        // Protocol
        factoryXml.setProtocol(endpoint.getProtocol().getValue());

        return factoryXml;
    }

    public AgreementPartyType renderParty(Party partyObject) {
        assert partyObject != null : "The party to be rendered is null!";
        Party party = (Party) partyObject;
        AgreementPartyType partyXml = AgreementPartyType.Factory.newInstance();

        // Annotation
        setAnnotation(party, partyXml);

        // AgreementRole
        STND agreementRole = party.getAgreementRole();
        partyXml.setRole(agreementRole.getValue());

        // ID
        partyXml.setID(party.getId().getValue());

        // Operative
        Operative[] operatives = party.getOperatives();
        if (operatives != null) {
            for (int i = 0; i < operatives.length; i++) {
                Operative operative = operatives[i];
                OperativeType operativeXml = partyXml.addNewOperative();

                // Annotation
                setAnnotation(operative, operativeXml);

                // ID
                operativeXml.setID(operative.getId().getValue());
            }
        }

        return partyXml;
    }

    public void setAnnotation(Annotated annotatedOrigin, Object annotatedTarget) {
        assert annotatedOrigin != null : "The annotated to be rendered is null!";
        assert annotatedTarget != null : "The annotated to be rendered is null!";
        Annotated annotated = (Annotated) annotatedOrigin;
        XmlObject xmlObject = (XmlObject) annotatedTarget;
        // First create the annotation as XML snippet
        AnnotatedDocument annotatedXml = AnnotatedDocument.Factory.newInstance();

        // Description
        if(annotated.getDescr() != null)
            annotatedXml.addNewAnnotated().setText(annotated.getDescr());
        else
            annotatedXml.addNewAnnotated().setText("");

        // Properties
        STND[] propertyKeys = annotated.getPropertyKeys();
        MapStndAny properties = annotatedXml.getAnnotated().addNewProperties();
        for (int i = 0; i < propertyKeys.length; i++) {
            StndAnyEntryType propertyXml = properties.addNewEntry();
            STND key = propertyKeys[i];
            String value = (String) annotated.getPropertyValue(key);
            propertyXml.setKey(key.getValue());
            XmlObject valueXml = propertyXml.addNewValue();
            XmlCursor valueCursor = valueXml.newCursor();
            valueCursor.toNextToken();
            valueCursor.insertChars(value);
            valueCursor.dispose();
        }

        // Move the annotation to the target object
        XmlCursor objectCursor = xmlObject.newCursor();
        objectCursor.toNextToken();
        XmlCursor annotatedCursor = annotatedXml.getAnnotated().newCursor();
        annotatedCursor.moveXmlContents(objectCursor);

        annotatedCursor.dispose();
        objectCursor.dispose();
    }

}

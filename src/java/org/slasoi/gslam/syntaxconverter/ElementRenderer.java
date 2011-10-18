package org.slasoi.gslam.syntaxconverter;

import org.slasoi.slamodel.core.Annotated;
import org.slasoi.slamodel.core.CompoundConstraintExpr;
import org.slasoi.slamodel.core.CompoundDomainExpr;
import org.slasoi.slamodel.core.ConstraintExpr;
import org.slasoi.slamodel.core.DomainExpr;
import org.slasoi.slamodel.core.EventExpr;
import org.slasoi.slamodel.core.FunctionalExpr;
import org.slasoi.slamodel.core.SimpleDomainExpr;
import org.slasoi.slamodel.core.TypeConstraintExpr;
import org.slasoi.slamodel.primitives.Expr;
import org.slasoi.slamodel.primitives.ValueExpr;
import org.slasoi.slamodel.service.ServiceRef;
import org.slasoi.slamodel.service.Interface.Operation;
import org.slasoi.slamodel.service.Interface.Operation.Property;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.Endpoint;
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.InterfaceRef;
import org.slasoi.slamodel.sla.Invocation;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.VariableDeclr;
import org.slasoi.slamodel.sla.business.ComponentProductOfferingPrice;
import org.slasoi.slamodel.sla.business.Penalty;
import org.slasoi.slamodel.sla.business.PriceModification;
import org.slasoi.slamodel.sla.business.Product;
import org.slasoi.slamodel.sla.business.ProductOfferingPrice;
import org.slasoi.slamodel.sla.business.Termination;
import org.slasoi.slamodel.sla.business.TerminationClause;

/**
 * 
 * @author Peter A. Chronz
 * 
 *         This abstract class provides an interface for rendering individual elements of the SLA model. Inputs are
 *         instances of the SLA-model's Java implementation. Return types are rendering-specific objects.
 * 
 */
public abstract class ElementRenderer {

    public abstract Object renderAgreementTerm(AgreementTerm agreementTerm) throws Exception;

    public abstract Object renderGuaranteed(Guaranteed guaranteed) throws Exception;

    public abstract Object renderGuaranteedAction(Guaranteed.Action guaranteedAction) throws Exception;

    public abstract Object renderGuaranteedActionDefn(Guaranteed.Action.Defn actionDefn) throws Exception;

    public abstract Object renderInvocation(Invocation invocation) throws Exception;

    public abstract Object renderValueExpr(ValueExpr valueExpr) throws Exception;

    public abstract Object renderFuncExpr(FunctionalExpr funcExpr) throws Exception;

    public abstract Object renderDomainExpr(DomainExpr domainExpr) throws Exception;

    public abstract Object renderCompoundDomainExpr(CompoundDomainExpr compoundDomainExpr) throws Exception;

    public abstract Object renderSimpleDomainExpr(SimpleDomainExpr simpleDomainExpr) throws Exception;

    public abstract Object renderEventExpr(EventExpr eventExpr) throws Exception;

    public abstract Object renderGuaranteedState(Guaranteed.State guaranteedState) throws Exception;

    public abstract Object renderVariableDeclr(VariableDeclr variableDeclr) throws Exception;

    public abstract Object renderConstraintExpr(ConstraintExpr constraintExpr) throws Exception;

    public abstract Object renderTypeConstraintExpr(TypeConstraintExpr typeConstraintExpr) throws Exception;

    public abstract Object renderCompoundConstraintExpr(CompoundConstraintExpr compoundConstraintExpr) throws Exception;

    public abstract Object renderInterfaceDeclr(InterfaceDeclr interfaceDeclr) throws Exception;

    public abstract Object renderInterfaceRef(InterfaceRef interfaceRef) throws Exception;

    public abstract Object renderInterfaceSpecification(
            org.slasoi.slamodel.service.Interface.Specification interfaceSpec) throws Exception;

    public abstract Object renderInterfaceOperation(Operation operation) throws Exception;

    public abstract Object renderInterfaceOperationProperty(Property property) throws Exception;

    public abstract Object renderEndpoint(Endpoint endpoint) throws Exception;

    public abstract Object renderParty(Party party) throws Exception;

    public abstract void setAnnotation(Annotated annotatedOrigin, Object annotatedTarget) throws Exception;

    public abstract Object renderExpr(Expr expr) throws Exception;

    public abstract Object renderServiceRef(ServiceRef serviceRef) throws Exception;

    public abstract Object renderPenalty(Penalty actionDefn);

    public abstract Object renderTerminationClause(TerminationClause actionDefn);

    public abstract Object renderProductOfferingPrice(ProductOfferingPrice actionDefn);

    public abstract Object renderProduct(Product product);

    public abstract Object renderComponentProductOfferingPrice(ComponentProductOfferingPrice price);

    public abstract Object renderPriceModification(PriceModification priceModification);

    public abstract Object renderTermination(Termination actionDefn);
}

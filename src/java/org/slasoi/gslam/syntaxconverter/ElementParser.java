package org.slasoi.gslam.syntaxconverter;

import org.slasoi.slamodel.core.Annotated;
import org.slasoi.slamodel.core.CompoundDomainExpr;
import org.slasoi.slamodel.core.ConstraintExpr;
import org.slasoi.slamodel.core.DomainExpr;
import org.slasoi.slamodel.core.EventExpr;
import org.slasoi.slamodel.core.FunctionalExpr;
import org.slasoi.slamodel.core.SimpleDomainExpr;
import org.slasoi.slamodel.primitives.Expr;
import org.slasoi.slamodel.primitives.ValueExpr;
import org.slasoi.slamodel.service.ServiceRef;
import org.slasoi.slamodel.service.Interface.Operation;
import org.slasoi.slamodel.sla.AgreementTerm;
import org.slasoi.slamodel.sla.Guaranteed;
import org.slasoi.slamodel.sla.InterfaceDeclr;
import org.slasoi.slamodel.sla.Invocation;
import org.slasoi.slamodel.sla.Party;
import org.slasoi.slamodel.sla.VariableDeclr;
import org.slasoi.slamodel.sla.Guaranteed.Action.Defn;
import org.slasoi.slamodel.sla.Party.Operative;
import org.slasoi.slamodel.sla.business.ComponentProductOfferingPrice;
import org.slasoi.slamodel.sla.business.PriceModification;
import org.slasoi.slamodel.sla.business.Product;

/**
 * 
 * @author Peter A. Chronz
 * 
 *         This abstract class provides an interface for parsing individual elements of the SLA model. Inputs are
 *         rendering-specific objects. Return-types are instances of the SLA-model's Java implementation.
 * 
 */
public abstract class ElementParser {

    public abstract AgreementTerm parseAgreementTerm(Object agreementTerm) throws Exception;

    public abstract Guaranteed.Action parseGuaranteedAction(Object guaranteedAction) throws Exception;

    public abstract Guaranteed.Action.Defn parseGuaranteedActionDefn(Object guaranteedActionDefn) throws Exception;

    public abstract Guaranteed.Action.Defn parseCustomAction(Object customAction) throws Exception;

    public abstract Guaranteed.Action.Defn parseCompoundAction(Object compoundAction) throws Exception;

    public abstract Guaranteed.Action.Defn parseBusinessAction(Object businessAction) throws Exception;

    public abstract Invocation parseInvocation(Object invocation) throws Exception;

    public abstract Guaranteed.State parseGuaranteedState(Object guaranteedState) throws Exception;

    public abstract Party parseAgreementParty(Object party) throws Exception;

    public abstract Operative parseOperative(Object operative) throws Exception;

    public abstract void setAnnotation(Annotated annotatedParsed, Object annotatedOrigin) throws Exception;

    public abstract InterfaceDeclr parseInterfaceDeclraration(Object interfaceDeclr) throws Exception;

    public abstract Operation parseInterfaceOperation(Object operation) throws Exception;

    public abstract Operation.Property parseInterfaceOperationProperty(Object iFaceOperationProperty) throws Exception;

    public abstract DomainExpr parseDomainExpr(Object domainExpr) throws Exception;

    public abstract CompoundDomainExpr parseCompoundDomainExpr(Object compoundDomainExpr) throws Exception;

    public abstract SimpleDomainExpr parseSimpleDomainExpr(Object simpleDomainExpr) throws Exception;

    public abstract ValueExpr parseValueExpr(Object value) throws Exception;

    public abstract EventExpr parseEventExpr(Object eventExpr) throws Exception;

    public abstract ConstraintExpr parseConstraintExpr(Object constraint) throws Exception;

    public abstract FunctionalExpr parseFunctionalExpr(Object funcExpr) throws Exception;

    public abstract VariableDeclr parseVariableDeclr(Object variableDeclr) throws Exception;

    public abstract Expr parseExpr(Object expr) throws Exception;

    public abstract ServiceRef parseServiceRef(Object serviceRef) throws Exception;

    public abstract Defn parseTerminationClause(Object terminationClauseXml);

    public abstract Defn parseProductOfferingPrice(Object productOfferingPriceXml) throws Exception;

    public abstract Product parseProduct(Object productXml) throws Exception;

    public abstract ComponentProductOfferingPrice parseComponentProdOfferingPrice(Object componentProdOfferingPriceXml);

    public abstract PriceModification parsePriceModification(Object priceModificationXml);

    public abstract Defn parsePenaltyAction(Object penaltyActionXml);

    public abstract Defn parseTerminationAction(Object terminationActionXml);
}

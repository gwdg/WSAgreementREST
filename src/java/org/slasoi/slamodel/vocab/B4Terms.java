package org.slasoi.slamodel.vocab;

import org.slasoi.slamodel.primitives.STND;
import org.slasoi.slamodel.primitives.UUID;
import org.slasoi.slamodel.vocab.meta;
import org.slasoi.slamodel.vocab.ext.Category;
import org.slasoi.slamodel.vocab.ext.Extensions;
import org.slasoi.slamodel.vocab.ext.Functional;
import org.slasoi.slamodel.vocab.ext.Nominal;

public class B4Terms implements Extensions{

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // STATIC CONSTANTS
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static final String $base = "http://www.slaatsoi.org/b4_terms#";
    
    // STRING VERSIONS
    public static final String $service_ccr_var = $base + "service_ccr_var";
    public static final String $service_reporting_interval = $base + "service_reporting_interval";
    public static final String $acceptable_service_violations = $base + "acceptable_service_violations";
    public static final String $service_data_classification = $base + "service_data_classification";
    public static final String $service_availability_restrictions = $base + "service_availability_restrictions";
    public static final String $vm_disk_throughput = $base + "vm_disk_throughput";
    public static final String $vm_net_throughput = $base + "vm_net_throughput";  
    public static final String $vm_data_encryption = $base + "vm_data_encryption";
    public static final String $vm_hard_disk = $base + "vm_hard_disk";
    public static final String $vm_network_bandwidth = $base + "vm_network_bandwidth";
    public static final String $vm_power_capping = $base + "vm_power_capping";
    public static final String $vm_snapshot_backup = $base + "vm_snapshot_backup";
    public static final String $vm_snapshot_retention = $base + "vm_snapshot_retention";
    public static final String $location = $base + "location";
    public static final String $auditability = $base + "auditability";
    public static final String $SAS70_compliant = $base + "SAS70_compliant";
    public static final String $service_data_retention = $base + "service_data_retention";
    public static final String $service_data_delete_method = $base + "service_data_delete_method";
    public static final String $service_hardware_redundancy_level = $base + "service_hardware_redundancy_level";

    
    
    // STND VERSIONS
    public static final STND service_ccr_var = new STND($service_ccr_var);
    public static final STND service_reporting_interval = new STND($service_reporting_interval);
    public static final STND acceptable_service_violations = new STND($acceptable_service_violations);
    public static final STND service_data_classification = new STND($service_data_classification);
    public static final STND service_availability_restrictions = new STND($service_availability_restrictions);
    public static final STND vm_disk_throughput = new STND($vm_disk_throughput);
    public static final STND vm_net_throughput = new STND($vm_net_throughput);
    public static final STND vm_data_encryption = new STND($vm_data_encryption);
    public static final STND vm_hard_disk = new STND($vm_hard_disk);
    public static final STND vm_network_bandwidth = new STND($vm_network_bandwidth);
    public static final STND vm_power_capping = new STND($vm_power_capping);
    public static final STND vm_snapshot_backup = new STND($vm_snapshot_backup);
    public static final STND vm_snapshot_retention = new STND($vm_snapshot_retention);
    public static final STND location = new STND($location);
    public static final STND auditability = new STND($auditability);
    public static final STND SAS70_compliant = new STND($SAS70_compliant);
    public static final STND service_data_retention = new STND($service_data_retention);
    public static final STND service_data_delete_method = new STND($service_data_delete_method);
    public static final STND service_hardware_redundancy_level = new STND($service_hardware_redundancy_level);


    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // IMPLEMENTATION OF org.slasoi.slamodel.vocab.ext.Extensions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private final UUID uuid = new UUID($base);
    private final Nominal[] nominals = new Nominal[]{};
    private final Functional[] functionals = new Functional[]{
            /* 
             * this "Functional" object defines the signature of the QoS term:
             *      my_qos_term( r : RESOURCE_TYPE_SERVICE ) : BOOLEAN
             */
            new Functional(service_ccr_var, new Category[]{ meta.RESOURCE_TYPE_SERVICE }, meta.BOOLEAN, false),
            new Functional(service_reporting_interval, new Category[]{ meta.RESOURCE_TYPE_SERVICE }, meta.TEXT, false),
            new Functional(acceptable_service_violations, new Category[]{ meta.RESOURCE_TYPE_SERVICE }, meta.COUNT, false),
            new Functional(service_data_classification, new Category[]{ meta.RESOURCE_TYPE_SERVICE }, meta.TEXT, false),
            new Functional(service_availability_restrictions, new Category[]{ meta.RESOURCE_TYPE_SERVICE }, meta.TEXT, false),
            new Functional(vm_disk_throughput, new Category[]{ meta.RESOURCE_TYPE_SERVICE }, meta.DATA_RATE, false),
            new Functional(vm_net_throughput, new Category[]{ meta.SERVICE }, meta.DATA_RATE, false),
            new Functional(vm_data_encryption, new Category[]{ meta.SERVICE }, meta.BOOLEAN, false),
            new Functional(vm_hard_disk, new Category[]{ meta.SERVICE }, meta.COUNT, false),
            new Functional(vm_network_bandwidth, new Category[]{ meta.SERVICE }, meta.DATA_RATE, false),
            new Functional(vm_power_capping, new Category[]{ meta.SERVICE }, meta.BOOLEAN, false),
            new Functional(vm_snapshot_backup, new Category[]{ meta.SERVICE }, meta.BOOLEAN, false),
            new Functional(vm_snapshot_retention, new Category[]{ meta.SERVICE }, meta.DURATION, false),
            new Functional(location, new Category[]{ meta.SERVICE }, meta.TEXT, false),
            new Functional(auditability, new Category[]{ meta.SERVICE }, meta.BOOLEAN, false),
            new Functional(SAS70_compliant, new Category[]{ meta.SERVICE }, meta.BOOLEAN, false),
            new Functional(service_data_retention, new Category[]{ meta.RESOURCE_TYPE_SERVICE }, meta.COUNT, false),
            new Functional(service_data_delete_method, new Category[]{ meta.RESOURCE_TYPE_SERVICE }, meta.TEXT, false),
            new Functional(service_hardware_redundancy_level, new Category[]{ meta.RESOURCE_TYPE_SERVICE }, meta.TEXT, false),
            
    };
    private final String[] days = new String[]{};
    private final String[] months = new String[]{};
    
    public UUID getUuid(){ return uuid; }
    public Nominal[] getNominals(){ return nominals; }
    public Functional[] getFunctionals(){ return functionals; }
    public String[] getDays(){ return days; }
    public String[] getMonths(){ return months; }
    
}

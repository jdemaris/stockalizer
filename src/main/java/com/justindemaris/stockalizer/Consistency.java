/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.justindemaris.stockalizer;

import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HConsistencyLevel;

/**
 *
 * @author justin
 */
public class Consistency implements ConsistencyLevelPolicy {

	public HConsistencyLevel get(OperationType ot) {
		return HConsistencyLevel.ONE;
	}

	public HConsistencyLevel get(OperationType ot, String string) {
		return HConsistencyLevel.ONE;
	}
	
}

package com.justindemaris.stockalizer;

import com.justindemaris.stockalizer.edgar.Edgar;
import com.justindemaris.stockalizer.edgar.Filing;
import com.justindemaris.stockalizer.edgar.FilingList;
import java.io.IOException;
import java.util.Arrays;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.cassandra.service.template.ColumnFamilyResult;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ColumnFamilyUpdater;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.factory.HFactory;

/**
 * Handles the core functionality of our Stockalizer app
 *
 * @author Justin DeMaris <justin.demaris@gmail.com>
 */
public class App {
	/**
	 * Our connection to our wide-band data store
	 */
	Cluster cluster;
	
	ColumnFamilyTemplate<String, String> template;
	
	Keyspace ksp;
	
	/**
	 * Our connection for SEC filings
	 */
	Edgar edgar;
	
	/**
	 * Sets up the application and prepares all of our data sources
	 */
	public App() {
		// Connect to Cassandra for data persistence
		cluster = HFactory.getOrCreateCluster("test-cluster", "localhost:9160");
		
		// Connect to Edgar
		edgar = new Edgar();
		
		// Get a list of all of the 10-K filings for Google
		try {
			FilingList list = edgar.findFilings("GOOG", "10-K");
			System.out.println("Found a total of " + list.filings.size() + " filings");
			
			for ( int i = 0; i < list.filings.size(); i++ ) {
				Filing filing = list.filings.get(i);
				System.out.println(filing.date + " - " + filing.type + " - " + filing.description);
			}
		} catch ( IOException e ) {
			System.out.println("Failed to read list of Google 10-K filings");
		}
	}
	
	public void doCassandraStuff() {
		KeyspaceDefinition keyspaceDef = cluster.describeKeyspace("MyKeySpace");
		
		if ( keyspaceDef == null ) {
			System.out.println("Creating schema...");
			createSchema();
		} else
			System.out.println("Schema already created");
				
		ksp = HFactory.createKeyspace("MyKeySpace", cluster, new Consistency());
		
		template = new ThriftColumnFamilyTemplate<String, String>(
			ksp,
			"ColumnFamilyName",
			StringSerializer.get(),
			StringSerializer.get()
		);

		update();
		
		System.out.println("Read Value: " + read());
		
		delete();
		
		System.out.println("Read Value: " + read());
	}
	
	public void createSchema() {
		ColumnFamilyDefinition cfDef = HFactory.createColumnFamilyDefinition(
			"MyKeySpace",
			"ColumnFamilyName",
			ComparatorType.BYTESTYPE
		);
		KeyspaceDefinition newKeySpace = HFactory.createKeyspaceDefinition(
			"MyKeySpace",
			ThriftKsDef.DEF_STRATEGY_CLASS,
			1,
			Arrays.asList(cfDef)
		);
		
		cluster.addKeyspace(newKeySpace, true);
	}
	
	public void delete() {
		try {
			template.deleteColumn("my key", "domain");
		} catch ( HectorException e ) {
			// do nothing
		}
	}
	
	public String read() {
		String value = null;
		try {
			ColumnFamilyResult<String, String> res = template.queryColumns("my key");
			value = res.getString("domain");
		} catch ( HectorException e) {
			value = "ERROR: " + e;
		}
		return value;
	}
	
	public void update() {
		ColumnFamilyUpdater<String, String> updater = template.createUpdater("my key");
		updater.setString("domain", "www.datastax.com");
		updater.setLong("time", System.currentTimeMillis());
		
		try {
			System.out.print("Updating...");
			template.update(updater);
			System.out.println("done");
		} catch ( HectorException e ) {
			System.out.println("failed");
			System.out.println("Problem: " + e);
		}
	}
	
	/**
	 * Entry Point for executing the JAR this produces
	 * 
	 * @param args 
	 */
    public static void main( String[] args ) {
		App app = new App();
    }
}

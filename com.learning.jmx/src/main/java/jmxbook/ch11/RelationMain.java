package jmxbook.ch11;

import jmxbook.ch3.RMIClientFactory;
import java.util.*;
import javax.management.*;
import javax.management.relation.*;

public class RelationMain
{
  ObjectName relationServiceName = null;
  Role voiceProcessorRole = null;
  Role faxProcessorRole = null;
  RoleList roleList = new RoleList();
  MBeanServerConnection client = null;

  public RelationMain()
  {
    client = RMIClientFactory.getClient();

    try
    {
      relationServiceName=new ObjectName("JMXBookAgent:name=relationService");
    }
    catch(Exception e)
    {
      e.printStackTrace();
      System.exit(0);
    }

  }

  public void createMBeans()
  {

    try
    {
      Object[] params = new Object[1];
      params[0] = new Integer(1);
      String[] sig = new String[1];
      sig[0] = "int";

      //register the first PhoneCard
      System.out.println("\n>>> REGISTERING PhoneCard1 MBean");
      ObjectName phoneCard1Name = new ObjectName("JMXBookAgent:name=phoneCard,slot=1");
      client.createMBean( "jmxbook.ch11.PhoneCard", phoneCard1Name, params, sig );

      //register the second PhoneCard
      params[0] = new Integer(2);
      System.out.println("\n>>> REGISTERING PhoneCard2 MBean");
      ObjectName phoneCard2Name = new ObjectName( "JMXBookAgent:name=phoneCard,slot=2" );
      client.createMBean( "jmxbook.ch11.PhoneCard", phoneCard2Name, params, sig );

      //register the FaxCard
      System.out.println("\n>>> REGISTERING FaxCard MBean");
      ObjectName faxCardName = new ObjectName( "JMXBookAgent:name=faxCard" );
      client.createMBean( "jmxbook.ch11.FaxCard",faxCardName );

      //register the RoutingTable
      System.out.println("\n>>> REGISTERING RoutingTable MBean");
      ObjectName routingTableName = new ObjectName( "JMXBookAgent:name=routingTable" );
      client.createMBean("jmxbook.ch11.RoutingTable", routingTableName);

    }
    catch(Exception e)
    {
      e.printStackTrace();
      System.out.println("Error Registering MBeans");
    }

  }

  public RoleInfo[] createRoleInfoArray()
  {
    RoleInfo[] roleInfoArray = new RoleInfo[3];

    try
    {
      roleInfoArray[0]= new RoleInfo("VoiceProcessor", "jmxbook.ch11.PhoneCard", true, true, 1, 10, "The Role for Phone Card");
      roleInfoArray[1] = new RoleInfo("FaxProcessor", "jmxbook.ch11.FaxCard", true, true, 1, 1, "The Role for Fax Card");
      roleInfoArray[2] = new RoleInfo("CallRouter", "jmxbook.ch11.RoutingTable", true, true, 1, 1, "The Role for Routing Table");
    }
    catch(Exception e)
    {
      System.out.println( "Error Creating the Relation Service MBean" );
      e.printStackTrace();
    }

    return roleInfoArray;
  }

  public void createRelationTypes( RoleInfo[] roleInfoArray )
  {
    try
    {
      Object[] params = new Object[2];
      params[0] = "myRelationType";
      params[1] = roleInfoArray;
      String[] signature = new String[2];
      signature[0] = "java.lang.String";

      try {
        signature[1] = (roleInfoArray.getClass()).getName();
      }
      catch (Exception exc)
      {
        throw exc;
      }

      client.invoke(relationServiceName, "createRelationType", params, signature);
    }
    catch (Exception e)
    {
      System.out.println("Error Creating the RelationType");
      e.printStackTrace();
    }
  }

  public void createRoles()
  {
    ArrayList voiceRoleValue = new ArrayList();
    ArrayList faxRoleValue = new ArrayList();
    ArrayList routingTableRoleValue = new ArrayList();

    try
    {
      voiceRoleValue.add( new ObjectName("JMXBookAgent:name=phoneCard,slot=1" ) );
      voiceRoleValue.add( new ObjectName("JMXBookAgent:name=phoneCard,slot=2") );
      Role voiceProcessorRole = new Role("VoiceProcessor", voiceRoleValue);
      faxRoleValue.add( new ObjectName("JMXBookAgent:name=faxCard") );
      Role faxProcessorRole = new Role("FaxProcessor", faxRoleValue);
      routingTableRoleValue.add( new ObjectName( "JMXBookAgent:name=routingTable" ) );
      Role routingTableRole = new Role("CallRouter", routingTableRoleValue);
      roleList.add(voiceProcessorRole);
      roleList.add(faxProcessorRole);
      roleList.add(routingTableRole);
    }
    catch (Exception e)
    {
      System.out.println("Error Creating Roles");
      e.printStackTrace();
    }

  }

  public void createRelationService()
  {
    ObjectName relationServiceName = null;

    try
    {
      relationServiceName = new ObjectName( "JMXBookAgent:name=relationService" );
      Object[] params = new Object[1];
      params[0] = new Boolean(true);
      String[] signature = new String[1];
      signature[0] = "boolean";
      client.createMBean( "javax.management.relation." +"RelationService", relationServiceName, params, signature);
    }
    catch(Exception e)
    {
      System.out.println("Error Creating the Relation" + " Service MBean");
      e.printStackTrace();
    }

  }

  public void createRelation()
  {
    System.out.println("\n>>> CREATE EXTERNAL RELATION of " + " type myRelationType");

    try
    {
      //register the relation MBean
      ObjectName relationMBeanName = new ObjectName( "JMXBookAgent:type=RelationMBean");

      Object[] params = new Object[4];
      params[0] = "RelationId1";
      params[1] = relationServiceName;
      params[2] = "myRelationType";
      params[3] = roleList;

      String[] sig = new String[4];
      sig[0] = "java.lang.String";
      sig[1] = relationServiceName.getClass().getName();
      sig[2] = "java.lang.String";
      sig[3] = roleList.getClass().getName();

      client.createMBean( "jmxbook.ch11.CtlRelation", relationMBeanName, params, sig );

      //add the new relation
      params = new Object[1];
      sig = new String[1];
      params[0] = relationMBeanName ;
      sig[0] = "javax.management.ObjectName";

      client.invoke( relationServiceName, "addRelation", params, sig );
    }
    catch(Exception e)
    {
      System.out.println("Could not create the relation");
      e.printStackTrace();
    }

  }

  public static void main( String[] args )
  {
    System.out.println("\n>>> START of Relation Service example");
    RelationMain example = new RelationMain();

    example.createMBeans();
    RoleInfo[] roleInfo = example.createRoleInfoArray();
    example.createRelationTypes(roleInfo);
    example.createRoles();
    example.createRelation();
    System.exit(0);
  }

}







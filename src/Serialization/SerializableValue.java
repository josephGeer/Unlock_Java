/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Serialization;

/**
 *
 * @author Josep
 */
public class SerializableValue implements java.io.Serializable {

    private int value;

    /**
     *
     * @param value
     */
    public SerializableValue(int value) {
	this.value=value;
    }

    /**
     *
     * @return la serializable value
     */
    public int getValue() {
	return value;
    }
  
    /**
     *
     * @param path_to_serial_file
     */
    public void saveValue(String path_to_serial_file) {
	java.io.ObjectOutputStream out=null;
	
	try {
	    out=new java.io.ObjectOutputStream(new java.io.FileOutputStream(path_to_serial_file));
	} catch(java.io.IOException ioe) {
	    System.err.println("An I/O error occurs while writing stream header");
	    System.err.println(ioe);
	    System.exit(10);
	} catch(java.lang.SecurityException se) {
	    System.err.println("An untrusted subclass illegally overrides security-sensitive methods");
	    System.err.println(se);
	    System.exit(20);
	} catch(java.lang.NullPointerException npe) {
	    System.err.println("The FileOutputStream is null");
	    System.err.println(npe);
	    System.exit(30);
	}
	
	try {
	    out.writeObject(this);
	} catch(java.io.InvalidClassException ice) {
	    System.err.println("Something is wrong with "+getClass().getName()+" class during serialization");
	    System.err.println(ice);
	    System.exit(40);
	} catch(java.io.NotSerializableException nse) {
	    System.err.println("Some object to be serialized does not implement the java.io.Serializable interface");
	    System.err.println(nse);
	    System.exit(50);
	} catch(java.io.IOException ioe) {
	    System.err.println("Any exception thrown by the underlying OutputStream.");
	    System.err.println(ioe);
	    System.exit(60);
	} finally {
	    try	{
		out.flush();
		out.close();
	    } catch(java.io.IOException ioe) {
		System.err.println("There is an I/O error during flush or close process.");
		System.err.println(ioe);
		System.exit(70);
	    }
	}
    }

    /**
     *
     * @param path_to_serial_file
     * @return la serializable value
     */
    public static SerializableValue loadValue(String path_to_serial_file) {
	java.io.ObjectInputStream in=null;
	
	SerializableValue value=null;
	
	try {
	    in=new java.io.ObjectInputStream(new java.io.FileInputStream(path_to_serial_file));
	    value=(SerializableValue)in.readObject();
	} catch(java.io.StreamCorruptedException sce) {
	    System.err.println("The stream header is incorrect during the opening of the stream or the object reading.");
	    System.err.println(sce);
	    System.exit(20);
	} catch(java.lang.SecurityException se)	{
	    System.err.println("An untrusted subclass illegally overrides security-sensitive methods.");
	    System.err.println(se);
	    System.exit(30);
	} catch(java.lang.NullPointerException npe) {
	    System.err.println("The FileInputStream is null.");
	    System.err.println(npe);
	    System.exit(40);
	} catch(java.lang.ClassNotFoundException cnfe) {
	    System.err.println("Class "+SerializableValue.class.getName()+" of the serialized object cannot be found.");
	    System.err.println(cnfe);
	    System.exit(50);
	} catch(java.io.InvalidClassException ice) {
	    System.err.println("Something is wrong with a class used by serialization.");
	    System.err.println(ice);
	    System.exit(60);
	} catch(java.io.OptionalDataException ode) {
	    System.err.println("Primitive data was found in the stream instead of objects.");
	    System.err.println(ode);
	    System.exit(70);
	} catch(java.io.IOException ioe) {
	    System.err.println("an I/O error occurs while reading stream header.");
	    System.err.println(ioe);
	    System.exit(80);
	} finally {
	    if(in != null) {
		try {
		    in.close();
		} catch(java.io.IOException ioe) {
		    System.err.println("There is an I/O error ...");
		    System.err.println(ioe);
		    System.exit(90);
		}
	    }
	}
	
	return value;
    }

    /**
     *
     * @param a
     * méthode pour changé la valeur
     */
    public void setValue(int a){
        this.value = a;
    }
}

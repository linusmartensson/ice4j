/*
 * ice4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 * Maintained by the SIP Communicator community (http://sip-communicator.org).
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */
package org.ice4j.attribute;

import org.ice4j.*;

/**
 * After the header are 0 or more attributes.  Each attribute is TLV
 * encoded, with a 16 bit type, 16 bit length, and variable value:
 *
 *     0                   1                   2                   3       <br/>
 *     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1     <br/>
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+    <br/>
 *    |         Type                  |            Length             |    <br/>
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+    <br/>
 *    |                             Value                             .... <br/>
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+    <br/>
 *<br/>
 *    The following types are defined:<br/>
 *<br/>
 * STUN attributes:<br/>
 *    0x0001: MAPPED-ADDRESS                                               <br/>
 *    0x0002: RESPONSE-ADDRESS                                             <br/>
 *    0x0003: CHANGE-REQUEST                                               <br/>
 *    0x0004: SOURCE-ADDRESS                                               <br/>
 *    0x0005: CHANGED-ADDRESS                                              <br/>
 *    0x0006: USERNAME                                                     <br/>
 *    0x0007: PASSWORD                                                     <br/>
 *    0x0008: MESSAGE-INTEGRITY                                            <br/>
 *    0x0009: ERROR-CODE                                                   <br/>
 *    0x000a: UNKNOWN-ATTRIBUTES                                           <br/>
 *    0x000b: REFLECTED-FROM                                               <br/>
 *    0x0014: REALM                                                        <br/>
 *    0x0015: NONCE                                                        <br/>
 *    0x0020: XOR-MAPPED-ADDRESS                                           <br/>
 *    0x8022: SOFTWARE                                                     <br/>
 *    0x8023: ALTERNATE-SERVER                                             <br/>
 *    0x8028: FINGERPRINT                                                  <br/>
 *                                                                         <br/>
 * TURN attributes:<br/>
 *    0x000C: CHANNEL-NUMBER                                               <br/>
 *    0x000D: LIFETIME                                                     <br/>
 *    0x0012: XOR-PEER-ADDRESS                                             <br/>
 *    0x0013: DATA                                                         <br/>
 *    0x0016: XOR-RELAYED-ADDRESS                                          <br/>
 *    0x0018: EVEN-PORT                                                    <br/>
 *    0x0019: REQUESTED-TRANSPORT                                          <br/>
 *    0x001A: DONT-FRAGMENT                                                <br/>
 *    0x0022: RESERVATION-TOKEN                                            <br/>
 *
 * ICE attributes:<br/>
 *    0x0024: PRIORITY                                                     <br/>
 *    0x0025: USE-CANDIDATE                                                <br/>
 *    0x8029: ICE-CONTROLLED                                               <br/>
 *    0x802A: ICE-CONTROLLING                                              <br/>
 *
 * @author Emil Ivov
 * @author Sebastien Vincent
 * @author Namal Senarathne
 */
public abstract class Attribute
{
    /* STUN attributes */
    public static final char MAPPED_ADDRESS = 0x0001;
    public static final char RESPONSE_ADDRESS = 0x0002;
    public static final char CHANGE_REQUEST = 0x0003;
    public static final char SOURCE_ADDRESS = 0x0004;
    public static final char CHANGED_ADDRESS = 0x0005;
    public static final char USERNAME = 0x0006;
    public static final char PASSWORD = 0x0007;
    public static final char MESSAGE_INTEGRITY = 0x0008;
    public static final char ERROR_CODE = 0x0009;
    public static final char UNKNOWN_ATTRIBUTES = 0x000a;
    public static final char REFLECTED_FROM = 0x000b;
    public static final char REALM = 0x0014;
    public static final char NONCE = 0x0015;
    public static final char XOR_MAPPED_ADDRESS = 0x0020;
    public static final char XOR_ONLY = 0x0021;
    public static final char SOFTWARE = 0x8022;
    public static final char ALTERNATE_SERVER = 0x8023;
    public static final char FINGERPRINT = 0x8028;
    public static final char UNKNOWN_OPTIONAL_ATTRIBUTE = 0x8000;

    /* TURN attributes */
    public static final char CHANNEL_NUMBER = 0x000c;
    public static final char LIFETIME = 0x000d;
    public static final char XOR_PEER_ADDRESS = 0x0012;
    public static final char DATA = 0x0013;
    public static final char XOR_RELAYED_ADDRESS = 0x0016;
    public static final char EVEN_PORT = 0x0018;
    public static final char REQUESTED_TRANSPORT = 0x0019;
    public static final char DONT_FRAGMENT = 0x001a;
    public static final char RESERVATION_TOKEN = 0x0022;

    /* ICE attributes */
    public static final char PRIORITY = 0x0024;
    public static final char USE_CANDIDATE = 0x0025;
    public static final char ICE_CONTROLLED = 0x8029;
    public static final char ICE_CONTROLLING = 0x802a;

    /**
     * The type of the attribute.
     */
    protected char attributeType = 0;

    /**
     * The size of an atribute header in bytes = len(TYPE) + len(LENGTH) = 4
     */
    public static final char HEADER_LENGTH = 4;

    /**
     * Creates an empty STUN message attribute.
     *
     * @param attributeType the type of the attribute.
     */
    protected Attribute(char attributeType)
    {
        setAttributeType(attributeType);
    }

    /**
     * Returns the length of this attribute's body.
     *
     * @return the length of this attribute's value.
     */
    public abstract char getDataLength();

    /**
     * Returns the human readable name of this attribute. Attribute names do
     * not really matter from the protocol point of view. They are only used
     * for debugging and readability.
     *
     * @return this attribute's name.
     */
    public abstract String getName();

    /**
     * Returns the attribute's type.
     *
     * @return the type of this attribute.
     */
    public char getAttributeType()
    {
        return attributeType;
    }

    /**
     * Sets the attribute's type.
     *
     * @param type the new type of this attribute
     */
    protected void setAttributeType(char type)
    {
        this.attributeType = type;
    }

   /**
    * Compares two STUN Attributes. Two attributes are considered equal when
    * they have the same type length and value.
    *
    * @param obj the object to compare this attribute with.
    *
    * @return true if the attributes are equal and false otherwise.
    */

    public abstract boolean equals(Object obj);

    /**
     * Returns a binary representation of this attribute.
     *
     * @return a binary representation of this attribute.
     */
    public abstract byte[] encode();

    /**
     * Sets this attribute's fields according to attributeValue array.
     *
     * @param attributeValue a binary array containing this attribute's field
     * values and NOT containing the attribute header.
     * @param offset the position where attribute values begin (most often
     * offset is equal to the index of the first byte after length)
     * @param length the length of the binary array.
     *
     * @throws StunException if attrubteValue contains invalid data.
     */
    abstract void decodeAttributeBody( byte[] attributeValue,
                                       char   offset,
                                       char   length)
        throws StunException;
}
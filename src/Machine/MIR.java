/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Machine;

/*! \brief  This class simulates the MIC1 MIR component.
*
 
 * @author Mong Mary Touch
 */

public class MIR {

    private char amux;
    private String cond;
    private String alu;
    private String sh;
    private char mbr;
    private char mar;
    private char rd;
    private char wr;
    private char enc;
    private String c_bus;
    private String b_bus;
    private String a_bus;
    private String addr;
    private String opcode;
    private String malLine;
    public MIR(String opcode, String mal) {
        this.malLine = mal;
        opcode = opcode.replaceAll("\\s+","");
        this.opcode = opcode;
        amux = opcode.charAt(0);
        cond = opcode.substring(1, 3);
        alu = opcode.substring(3, 5);
        sh = opcode.substring(5, 7);
        mbr = opcode.charAt(7);
        mar = opcode.charAt(8);
        rd = opcode.charAt(9);
        wr = opcode.charAt(10);
        enc = opcode.charAt(11);
        c_bus = opcode.substring(12, 16);
        b_bus = opcode.substring(16, 20);
        a_bus = opcode.substring(20, 24);
        addr = opcode.substring(24, opcode.length());
    }
<<<<<<< HEAD
    public String getMAL(){
        return this.malLine;
    }
=======
    
>>>>>>> 0d8593f5013ad0461213211f5b34f51523fb76f9
    
    public String getAll() {
        return this.amux + this.cond + this.alu + this.sh + this.mbr + this.mar + this.rd + this.wr + this.enc + this.c_bus + this.a_bus + this.b_bus + this.addr;
    }

    public String getAlu() {
        return alu;
    }

    public String getSh() {
        return sh;
    }

    public char getMbr() {
        return mbr;
    }

    public char getMar() {
        return mar;
    }

    public char getRd() {
        return rd;
    }

    public char getWr() {
        return wr;
    }

    public char getEnc() {
        return enc;
    }

    public String getC_bus() {
        return c_bus;
    }

    public String getB_bus() {
        return b_bus;
    }

    public String getA_bus() {
        return a_bus;
    }

    public String getAddr() {
        return addr;
    }

    public char getAmux() {
  
        return this.amux;
    }

    public String getCond() {
        return this.cond;
    }

    public void setAll(String all) {
        this.opcode = all;
    }

    public void setAmux(char amux) {
        this.amux = amux;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public void setAlu(String alu) {
        this.alu = alu;
    }

    public void setSh(String sh) {
        this.sh = sh;
    }

    public void setMbr(char mbr) {
        this.mbr = mbr;
    }

    public void setMar(char mar) {
        this.mar = mar;
    }

    public void setRd(char rd) {
        this.rd = rd;
    }

    public void setWr(char wr) {
        this.wr = wr;
    }

    public void setEnc(char enc) {
        this.enc = enc;
    }

    public void setC_bus(String c_bus) {
        this.c_bus = c_bus;
    }

    public void setB_bus(String b_bus) {
        this.b_bus = b_bus;
    }

    public void setA_bus(String a_bus) {
        this.a_bus = a_bus;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

}

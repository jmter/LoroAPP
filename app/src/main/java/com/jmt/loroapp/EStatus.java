package com.jmt.loroapp;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EStatus {

    private String address;
    private String latitude;
    private String longitude;
    private String vBat;
    private String velocidad;
    private boolean st;
    private String tHead;
    private String pOil;
    private int id;
    private String horas;
    private String dia;
    private String mes;
    private String ano;
    private String hora;
    private String min;

    @Exclude private Calendar calendar;
    @Exclude
    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        Calendar calendar = new GregorianCalendar(Integer.valueOf(ano)+2000,Integer.valueOf(mes),Integer.valueOf(dia),Integer.valueOf(hora),Integer.valueOf(min),0);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 3);
        hora = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        min = String.valueOf(calendar.get(Calendar.MINUTE));
        dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        mes = String.valueOf(calendar.get(Calendar.MONTH));
        ano = String.valueOf(calendar.get(Calendar.YEAR));
        this.calendar = calendar;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public boolean isSt() {
        return st;
    }

    public String gettHead() {
        return tHead;
    }

    public String getpOil() {
        return pOil;
    }

    public String getHoras() {
        return horas;
    }

    public String getDia() {
        return dia;
    }

    public String getMes() {
        return mes;
    }

    public String getAno() {
        return ano;
    }

    public String getHora() {
        return hora;
    }

    public String getMin() {
        return min;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getvBat() {
        return vBat;
    }

    public void setvBat(String vBat) {
        this.vBat = vBat;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public void settHead(String tHead) {
        this.tHead = tHead;
    }

    public void setpOil(String pOil) {
        this.pOil = pOil;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public void bodyPross(String body){
        if(body != null) {
            for (int idx = 0; idx < body.length(); idx++) {
                char c = body.charAt(idx);
                StringBuilder value = new StringBuilder();
                idx++;
                char num = body.charAt(idx);
                while (num != ',') {
                    value.append(num);
                    idx++;
                    if ((idx) >= body.length()) {
                        break;
                    }
                    num = body.charAt(idx);
                }
                switch (c) {
                    case 'A':
                        latitude = value.toString();
                        break;
                    case 'O':
                        longitude = value.toString();
                        break;
                    case 'H':
                        horas = value.toString();
                        break;
                    case 'T':
                        StringBuilder time = new StringBuilder();
                        time.append(value.charAt(0));
                        time.append(value.charAt(1));
                        dia = time.toString();
                        time.delete(0, 10);
                        time.append(value.charAt(2));
                        time.append(value.charAt(3));
                        mes = time.toString();
                        time.delete(0, 10);
                        time.append(value.charAt(4));
                        time.append(value.charAt(5));
                        ano = time.toString();
                        time.delete(0, 10);
                        time.append(value.charAt(6));
                        time.append(value.charAt(7));
                        hora = time.toString();
                        time.delete(0, 10);
                        time.append(value.charAt(8));
                        time.append(value.charAt(9));
                        min = time.toString();
                        break;
                    case 'S':
                        if (value.toString().equals("1")) {
                            st = true;
                        } else {
                            st = false;
                        }
                        break;
                    case 'P':
                        pOil = value.toString();
                        break;
                    case 'Y':
                        tHead = value.toString();
                        break;
                    case 'V':
                        vBat = value.toString();
                        break;
                    case 'G':
                        velocidad = value.toString();
                        break;
                    default:
                        break;
                }

            }

            setCalendar();
        }
    }

    public String getTimeID(){
        String timeID;
        timeID = ano;
        if(Integer.parseInt(mes) < 10){
            timeID = timeID + "0" + mes;
        }else{
            timeID = timeID + mes;
        }
        if(Integer.parseInt(dia) < 10){
            timeID = timeID + "0" + dia;
        }else{
            timeID = timeID + dia;
        }
        if(Integer.parseInt(hora) < 10){
            timeID = timeID + "0" + hora;
        }else{
            timeID = timeID + hora;
        }
        if(Integer.valueOf(min) < 10){
            timeID = timeID + "0" + min;
        }else{
            timeID = timeID + min;
        }
        return timeID;
    }
}

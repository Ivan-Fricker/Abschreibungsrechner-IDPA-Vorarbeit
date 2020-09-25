/* Author: Jekathmenan Selvarajah
 * Project: Abschreibungsrechner To change this license header, choose License Headers in Project Properties.
 * Project: Abschreibungsrechner To change this template file, choose Tools | Templates
 * Project: Abschreibungsrechner and open the template in the editor.
 */

package ch.bbbaden.idpa.abschreibungen.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Jekathmenan
 */
public class AbschreibungDaten 
{
    private final int jahr;
    private final double betrag;
    private double rwAnlageKonto = -1;
    private double betragWBKonto = -1;
    private double rwNachNutzDauer = -1;
    MathContext m = new MathContext(6);

    public AbschreibungDaten(int jahr, double betrag)
    {
        this.jahr = jahr;
        this.betrag = betrag;
    }

    public AbschreibungDaten(int jahr, double betrag, double betragWBKonto)
    {
        this.jahr = jahr;
        this.betrag = betrag;
        this.betragWBKonto = betragWBKonto;
    }
    
    private int getZiffern(double wert)
    {
        int tmpWert = (int) wert;
        String strWert = Integer.toString(tmpWert);
        
        return strWert.length() + 2;
    }

    public String getJahr()
    {
        return Integer.toString(jahr);
    }

    public String getBetrag()
    {
        int ziff = getZiffern(betrag);
        BigDecimal bdBetrag = new BigDecimal(Double.toString(betrag)).round(new MathContext(ziff, RoundingMode.HALF_UP));
        bdBetrag.setScale(2, RoundingMode.HALF_UP);
        
        double doubleVal = Math.round(betrag * 100 + 0.005) / 100.0;
        
        
        return bdBetrag.toString();
    }

    public String getRwAnlageKonto()
    {
        if(rwAnlageKonto == -1)
        {
            return "-";
        }
        // berechnet benötigte Rundungsstellen
        int ziff = getZiffern(rwAnlageKonto);
        
        // rundet und gibt zurück
        BigDecimal bdRWAnlageKonto = new BigDecimal(Double.toString(rwAnlageKonto)).round(new MathContext(ziff, RoundingMode.HALF_UP));
        return bdRWAnlageKonto.toString();
    }

    public void setRwAnlageKonto(double rwAnlageKonto)
    {
        this.rwAnlageKonto = rwAnlageKonto;
    }

    public String getBetragWBKonto()
    {
        if(betragWBKonto == -1)
        {
            return "-";
        }
        return Double.toString(betragWBKonto);
    }

    public void setBetragWBKonto(double betragWBKonto)
    {
        this.betragWBKonto = betragWBKonto;
    }

    public String getRwNachNutzDauer()
    {
        if(rwNachNutzDauer == -1)
        {
            return "-";
        }
        return Double.toString(rwNachNutzDauer);
    }

    public void setRwNachNutzDauer(double rwNachNutzDauer)
    {
        this.rwNachNutzDauer = rwNachNutzDauer;
    }
}

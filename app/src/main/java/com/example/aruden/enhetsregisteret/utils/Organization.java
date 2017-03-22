package com.example.aruden.enhetsregisteret.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

// TODO gjøre om Serializable til Parcelable
// Parcelable er kjappere, men krever mer for å implementere
// TODO fjern set-funksjoner.
// Alle variabler skal kun settes i constructor
public class Organization implements Serializable {
    private String organisasjonsnummer;
    private String navn;
    private String stiftelsesdato;
    private String registreringsdatoEnhetsregisteret;
    private String organisasjonsform;
    private String hjemmeside;
    private String registrertIFrivillighetsregisteret;
    private String registrertIMvaregisteret;
    private String registrertIForetaksregisteret;
    private String registrertIStiftelsesregisteret;
    private String frivilligRegistrertIMvaregisteret;
    private String antallAnsatte;
    private String sisteInnsendteAarsregnskap;
    private String konkurs;
    private String underAvvikling;
    private String underTvangsavviklingEllerTvangsopplosning;
    private String overordnetEnhet;
    private KodeBesk institusjonellSektorkode;
    private KodeBesk naeringskode1;
    private KodeBesk naeringskode2;
    private KodeBesk naeringskode3;
    private Address postadresse;
    private Address forretningsadresse;

    public Organization(JSONObject org) {
        try {
            organisasjonsnummer = org.getString("organisasjonsnummer");
        } catch (JSONException e) {
            organisasjonsnummer = "empty";
        }
        try {
            navn = org.getString("navn");
        } catch (JSONException e) {
            navn = "empty";
        }
        try {
            stiftelsesdato = org.getString("stiftelsesdato");
        } catch (JSONException e) {
            stiftelsesdato = "empty";
        }
        try {
            registreringsdatoEnhetsregisteret = org.getString("registreringsdatoEnhetsregisteret");
        } catch (JSONException e) {
            registreringsdatoEnhetsregisteret = "empty";
        }
        try {
            organisasjonsform = org.getString("organisasjonsform");
        } catch (JSONException e) {
            organisasjonsform = "empty";
        }
        try {
            hjemmeside = org.getString("hjemmeside");
        } catch (JSONException e) {
            hjemmeside = "empty";
        }
        try {
            registrertIFrivillighetsregisteret = org.getString("registrertIFrivillighetsregisteret");
        } catch (JSONException e) {
            registrertIFrivillighetsregisteret = "empty";
        }
        try {
            registrertIMvaregisteret = org.getString("registrertIMvaregisteret");
        } catch (JSONException e) {
            registrertIMvaregisteret = "empty";
        }
        try {
            registrertIForetaksregisteret = org.getString("registrertIForetaksregisteret");
        } catch (JSONException e) {
            registrertIForetaksregisteret = "empty";
        }
        try {
            registrertIStiftelsesregisteret = org.getString("registrertIStiftelsesregisteret");
        } catch (JSONException e) {
            registrertIStiftelsesregisteret = "empty";
        }
        try {
            // TODO Sjekk dataformateringen in APIet
            // Denne kan inneholde flere verdier
            frivilligRegistrertIMvaregisteret = org.getString("frivilligRegistrertIMvaregisteret");
        } catch (JSONException e) {
            frivilligRegistrertIMvaregisteret = "empty";
        }
        try {
            antallAnsatte = org.getString("antallAnsatte");
        } catch (JSONException e) {
            antallAnsatte = "empty";
        }
        try {
            sisteInnsendteAarsregnskap = org.getString("sisteInnsendteAarsregnskap");
        } catch (JSONException e) {
            sisteInnsendteAarsregnskap = "empty";
        }
        try {
            konkurs = org.getString("konkurs");
        } catch (JSONException e) {
            konkurs = "empty";
        }
        try {
            underAvvikling = org.getString("underAvvikling");
        } catch (JSONException e) {
            underAvvikling = "empty";
        }
        try {
            underTvangsavviklingEllerTvangsopplosning = org.getString("underTvangsavviklingEllerTvangsopplosning");
        } catch (JSONException e) {
            underTvangsavviklingEllerTvangsopplosning = "empty";
        }
        try {
            overordnetEnhet = org.getString("overordnetEnhet");
        } catch (JSONException e) {
            overordnetEnhet = "empty";
        }
        //setForretningsadresse(org);
        //setPostadresse(org);
    }

    private class KodeBesk {
        private String kode;
        private String beskrivelse;

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getBeskrivelse() {
            return beskrivelse;
        }

        public void setBeskrivelse(String beskrivelse) {
            this.beskrivelse = beskrivelse;
        }
    }

    private class Address {
        private String adresse;
        private String postnummer;
        private String poststed;
        private String kommunenummer;
        private String kommune;
        private String landkode;
        private String land;

        private Address(JSONObject address, boolean error) {
            if (!error) {
                try {
                    this.adresse = address.getString("adresse");
                } catch (JSONException e) {
                    this.adresse = "empty";
                }
                try {
                    this.postnummer = address.getString("postnummer");
                } catch (JSONException e) {
                    this.postnummer = "empty";
                }
                try {
                    this.poststed = address.getString("poststed");
                } catch (JSONException e) {
                    this.poststed = "empty";
                }
                try {
                    this.kommunenummer = address.getString("kommunenummer");
                } catch (JSONException e) {
                    this.kommunenummer = "empty";
                }
                try {
                    this.kommune = address.getString("kommune");
                } catch (JSONException e) {
                    this.kommune = "empty";
                }
                try {
                    this.landkode = address.getString("landkode");
                } catch (JSONException e) {
                    this.landkode = "empty";
                }
                try {
                    this.land = address.getString("land");
                } catch (JSONException e) {
                    this.land = "empty";
                }
            } else {
                this.adresse = "empty";
                this.postnummer = "empty";
                this.poststed = "empty";
                this.kommunenummer = "empty";
                this.kommune = "empty";
                this.landkode = "empty";
                this.land = "empty";
            }
        }

        public String getAdresse() {
            return adresse;
        }

        public String getPostnummer() {
            return postnummer;
        }

        public String getPoststed() {
            return poststed;
        }

        public String getKommunenummer() {
            return kommunenummer;
        }

        public String getKommune() {
            return kommune;
        }

        public String getLandkode() {
            return landkode;
        }

        public String getLand() {
            return land;
        }
    }

    public String getOrganisasjonsnummer() {
        return organisasjonsnummer;
    }

    public String getNavn() {
        return navn;
    }

    public String getStiftelsesdato() {
        return stiftelsesdato;
    }

    public String getRegistreringsdatoEnhetsregisteret() {
        return registreringsdatoEnhetsregisteret;
    }

    public String getOrganisasjonsform() {
        return organisasjonsform;
    }

    public String getHjemmeside() {
        return hjemmeside;
    }

    public String getRegistrertIFrivillighetsregisteret() {
        return registrertIFrivillighetsregisteret;
    }

    public String getRegistrertIMvaregisteret() {
        return registrertIMvaregisteret;
    }

    public String getRegistrertIForetaksregisteret() {
        return registrertIForetaksregisteret;
    }

    public String getRegistrertIStiftelsesregisteret() {
        return registrertIStiftelsesregisteret;
    }

    public String getFrivilligRegistrertIMvaregisteret() {
        return frivilligRegistrertIMvaregisteret;
    }

    public String getAntallAnsatte() {
        return antallAnsatte;
    }

    public String getSisteInnsendteAarsregnskap() {
        return sisteInnsendteAarsregnskap;
    }

    public String getKonkurs() {
        return konkurs;
    }

    public String getUnderAvvikling() {
        return underAvvikling;
    }

    public String getUnderTvangsavviklingEllerTvangsopplosning() {
        return underTvangsavviklingEllerTvangsopplosning;
    }

    public String getOverordnetEnhet() {
        return overordnetEnhet;
    }

    public KodeBesk getInstitusjonellSektorkode() {
        return institusjonellSektorkode;
    }

    public KodeBesk getNaeringskode1() {
        return naeringskode1;
    }

    public KodeBesk getNaeringskode2() {
        return naeringskode2;
    }

    public KodeBesk getNaeringskode3() {
        return naeringskode3;
    }

    public Address getPostadresse() {
        return postadresse;
    }

    public Address getForretningsadresse() {
        return forretningsadresse;
    }

    private void setPostadresse(JSONObject object) {
        JSONObject jsonAddr = new JSONObject();
        boolean error = false;
        try {
            jsonAddr = object.getJSONObject("postadresse");
        } catch (Throwable e) {
            error = true;
        }
        this.postadresse = new Address(jsonAddr, error);
    }

    private void setForretningsadresse(JSONObject object) {
        JSONObject jsonAddr = new JSONObject();
        boolean error = false;
        try {
            jsonAddr = object.getJSONObject("forretningsadresse");
        } catch (Throwable e) {
            error = true;
        }
        this.forretningsadresse = new Address(jsonAddr, error);
    }
}

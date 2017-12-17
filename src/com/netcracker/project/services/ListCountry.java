package com.netcracker.project.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class ListCountry {

  String[] countries = Locale.getISOCountries();

  public Collection<String> getCountriesCode(){
    Collection<String> countriesCodes = new ArrayList<>();
    Locale.setDefault(Locale.ENGLISH);
    for (String countryCode : countries) {
      Locale result = new Locale("en",countryCode);
      countriesCodes.add(result.getCountry());
    }
    return countriesCodes;
  }

  public Collection<String> getCountriesNames(){
    Collection<String> countriesNames = new ArrayList<>();
    Locale.setDefault(Locale.ENGLISH);
    for (String countryName : countries) {
      Locale result = new Locale("en",countryName);
      countriesNames.add(result.getDisplayCountry());
    }
    return countriesNames;
  }
}

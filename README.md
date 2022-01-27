# TigerCard

#FareCalculator is the main class which is able to calculate the fares by applying the daily and weekly limits
#Configuration acts as a facade and combines the TravelTimeConfigurationss, FareCappingConfigurations and ZoneFeesConfigurations which load the respective configs
#to help the FareCalculator to make the correct decision

#This application is made flexible in order to easily add more zones, change the existing traveltime configs, fare capping configs as well as zone fees configs 

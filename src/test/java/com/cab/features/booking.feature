Feature: Booking Page Validation


@Scenario1
Scenario: Submit form with all valid data
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid data:
	| Name     | Phone      | Email          | Trip | Cab   | CarType | Date       | Time  | Passenger | TripType    |
	| Srijita  | 9876543210 | sri@test1.com  | long | Micro | AC      | 2025-07-29 | 10:30 | 4         | roundtrip   |
	And User clicks Book Now button
	Then Booking confirmation message "Your Booking has been Confirmed" should be displayed
	

@Scenario2
Scenario: Submit form with invalid name format
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid data:
	| Name     | Phone      |  Email            | Trip  | Cab  | CarType     | Date       | Time  | Passenger | TripType |
	| <Name>   | 9876543210 | sri@test.com      | local | Mini | Non-Ac      | 2025-08-28 | 09:30 | 2         | oneway   |
	And User clicks Book Now button
	Then Error message "Please enter valid name" should be shown under Name field
	 Examples:
    | Name         |
    | 123          |  
    | Sri@1ab      |  
	

@Scenario3
Scenario: Submit form with invalid email format
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid data:
	| Name  | Phone      | Email        | Trip  | Cab  | CarType     | Date       | Time  | Passenger | TripType |
	| Sri   | 9876543210 | <Email>      | local | Mini | Non-Ac      | 2025-08-28 | 09:30 | 2         | oneway |
	And User clicks Book Now button
	Then Error message "Please enter valid email" should be shown under Email field
	 Examples:
    | Email       |
    | test.com    |  
    | sri234      |  
	

@Scenario4
Scenario Outline: Submit form with invalid phone number format
  Given User launches the browser and opens the cab booking page
  When User fills the form with valid data:
    | Name  | Phone       | Email         | Trip  | Cab  | CarType | Date       | Time  | Passenger | TripType |
    | Sri   | <Phone>     | test@test.com | local | Mini | Non-Ac  | 2025-08-28 | 09:30 | 2         | oneway   |
  And User clicks Book Now button
  Then Error message "Please enter valid phone number" should be shown under Phone field

  Examples:
    | Phone       |
    | 987654321   |  
    | 98765432101 |  
 
 
 @Scenario5
Scenario: Submit form keeping name empty
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid data:
	| Name | Phone      | Email         | Trip  | Cab  | CarType  | Date        | Time   | Passenger | TripType |
	|      | 9876543210 | sri@test1.com | local | Mini | AC       | 2025-08-25  | 09:30  | 5         | oneway   |
	And User clicks Book Now button
	Then Error message "Please enter the name" should be shown under Name field
	

@Scenario6
Scenario: Submit form keeping trip type empty
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid data:
	| Name    | Phone      | Email             | Trip | Cab   | CarType | Date       | Time  | Passenger | TripType |
	| Krishna | 9876543210 | hrishna@test1.com |      | Micro | AC      | 2025-07-28 | 09:30 | 2         | oneway   |
	And User clicks Book Now button
	Then Error message "Please Select the Trip" should be shown under Trip selection
	
@Scenario7
Scenario: Submit form keeping cab type empty
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid data:
	| Name    | Phone      | Email             | Trip | Cab   | CarType | Date       | Time  | Passenger | TripType |
	| Krishna | 9876543210 | hrishna@test1.com | long |       | AC      | 2025-07-28 | 09:30 | 2         | oneway   |
	And User clicks Book Now button
	Then Error message "Please Select the Cab Type" should be shown under Cab Type selection
	
@Scenario8
Scenario: Submit form keeping no of passengers empty
	Given User launches the browser and opens the cab booking page
	When User fills the form with valid data:
	| Name | Phone      | Email          | Trip  | Cab  | CarType | Date       | Time  | Passenger | TripType |
	| Atul | 9876543210 | atul@test1.com | long  | Mini | AC      | 2025-07-28 | 10:30 |           | oneway   |
	And User clicks Book Now button
	Then Error message "Please Select the number of passengers" should be shown under Passenger count


@Scenario9
Scenario: Booking with row 0 from Excel data
	Given User reads booking data from Excel sheet "BookingData" and row 0
	When User fills the form using Excel data
	And User clicks Book Now button
	Then Email error message "Please enter the email" should be displayed
	

@Scenario10
Scenario: Booking with row 1 from Excel data
	Given User reads booking data from Excel sheet "BookingData" and row 1
	When User fills the form using Excel data
	And User clicks Book Now button
	Then Booking confirmation message "Your Booking has been Confirmed" should be displayed
	

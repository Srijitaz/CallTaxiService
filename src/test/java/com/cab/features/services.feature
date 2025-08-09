Feature: Service Page Cab Link Navigation

Scenario Outline: Verify that cab service link is working
	Given User opens the Services page
	When User clicks on "<Cab Type>" service link
	Then The user should be navigated to "<Target Page>"

Examples:
  | Cab Type | Target Page   |
  | Mini     | mini.html     |
  | Micro    | micro.html    |
  | Sedan    | sedan.html    |
  | Suv      | suv.html      |
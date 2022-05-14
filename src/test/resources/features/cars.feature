Feature: Shopping

  Scenario Outline: Get all cars at the database
    Given I have <numberOfTheCars> cars at the database
    When I call the resource to obtain all cars
    Then The car list received should have <numberOfTheCarsExpected> cars

    Examples:
      | numberOfTheCars | numberOfTheCarsExpected |
      | 2               | 2                       |

Feature: Shopping

  Scenario Outline: Get all cars at the database
    Given I have <numbersOfCarsInTheDatabase> cars at the database
    When I call the resource to obtain all cars
    Then The car list received should have <expectedNumbersOfCars> cars

    Examples:
      | numbersOfCarsInTheDatabase | expectedNumbersOfCars |
      | 2                          | 2                     |

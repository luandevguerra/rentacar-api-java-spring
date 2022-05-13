Feature: Shopping

  Scenario: Get all cars at the database
    Given I have two cars at the database
    When I call the resource to obtain all cars

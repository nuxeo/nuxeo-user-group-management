Feature: New User

  I have a new user page

  Background:
    When I login as "Administrator"
    And I go to the Users and Groups Management

  Scenario: View New User page
    When I click the "New User" button
    Then I can see the New User page

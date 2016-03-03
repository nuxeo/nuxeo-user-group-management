Feature: New Group

  I have a new group page

  Background:
    When I login as "Administrator"
    And I go to the Users and Groups Management

  Scenario: View New Group page
    When I click the "New Group" button
    Then I can see the New Group page

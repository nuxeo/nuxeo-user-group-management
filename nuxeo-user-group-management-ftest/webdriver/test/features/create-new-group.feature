Feature: Create New Group

  I can create a new group

  Background:
    When I login as "Administrator"
    And I go to the Users and Groups Management

  Scenario: Create an Empty Group
    Given I am on the "New Group" page
    When I create a new group named "test_group"
    Then I can see the Users and Groups Management page
    And I can see the toast "Group test_group created"
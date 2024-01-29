// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

struct Todo {
    string name;
    bool isDone;
}

function done(Todo storage self) {
    self.isDone = true;
}

using {done} for Todo;

contract TodoHandler {

    address owner;
    mapping(address => Todo[]) todos;
    Todo[] test;
    

    constructor() {
        owner = msg.sender;
    }


    function getAllTodos(address addr) public checkOwner() view returns(Todo[] memory) {
        return todos[addr];
    }

    function deleteAllTodos(address addr) checkOwner() public {
        delete todos[addr];
    }

    function getMyOpenTodos() public view returns(Todo[] memory) {
        uint counter = 0;

        for(uint i = 0; i < todos[msg.sender].length; i++) {
            bytes memory tempEmptyStringTest = bytes(todos[msg.sender][i].name);
            if(todos[msg.sender][i].isDone == false && tempEmptyStringTest.length != 0) {               
                counter++;
            }
        }
        Todo[] memory openeTodosFiltered = new Todo[](counter);
        counter = 0;
        for(uint i = 0; i < openeTodosFiltered.length; i++) {
            bytes memory tempEmptyStringTest = bytes(todos[msg.sender][i].name);
            if(todos[msg.sender][i].isDone == false && tempEmptyStringTest.length != 0) {     
                openeTodosFiltered[counter] = (todos[msg.sender][i]);          
                counter++;
            }
        }
        return openeTodosFiltered;
    }

    function getMyDoneTodos() public view returns(Todo[] memory) {
        uint counter = 0;

        for(uint i = 0; i < todos[msg.sender].length; i++) {
            bytes memory tempEmptyStringTest = bytes(todos[msg.sender][i].name);
            if(todos[msg.sender][i].isDone == true && tempEmptyStringTest.length != 0) {               
                counter++;
            }
        }
        Todo[] memory doneTodosFiltered = new Todo[](counter);
        counter = 0;
        for(uint i = 0; i < doneTodosFiltered.length; i++) {
            bytes memory tempEmptyStringTest = bytes(todos[msg.sender][i].name);
            if(todos[msg.sender][i].isDone == true && tempEmptyStringTest.length != 0) {     
                doneTodosFiltered[counter] = (todos[msg.sender][i]);          
                counter++;
            }
        }
        return doneTodosFiltered;
    }

    function addMyTodo(string memory name) public {
        Todo memory todo = Todo(name, false);
        todos[msg.sender].push(todo);
    }

    function finishMyTodo(string memory name) public {
        for(uint i = 0; i < todos[msg.sender].length; i++) {
            if(compareStrings(todos[msg.sender][i].name, name)) {
                todos[msg.sender][i].isDone = true;
            }
        }
    }

    function compareStrings(string memory a, string memory b) public pure returns (bool) {
        return (keccak256(abi.encodePacked((a))) == keccak256(abi.encodePacked((b))));
    }

    function getOwner() public view returns(address) {
        return owner;
    }

    function changeOwner(address newOwner) public checkOwner() {
        owner = newOwner;
    }

    modifier checkOwner() {
        assert(msg.sender == owner);
        _;
    }

}
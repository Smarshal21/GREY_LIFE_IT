// SPDX-License-Identifier: MIT

pragma solidity ^0.8.19;

contract StringArrayContract {
    string[] public stringArray;

    function setStringArray(string[] memory _array) public {
        stringArray = _array;
    }

    function getStringArray() public view returns (string[] memory) {
        return stringArray;
    }
}

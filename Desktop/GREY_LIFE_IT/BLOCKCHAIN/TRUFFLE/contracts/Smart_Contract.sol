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
// SPDX-License-Identifier: MIT

pragma solidity ^0.8.19;

import "https://github.com/OpenZeppelin/openzeppelin-solidity/contracts/access/Ownable.sol";

contract RiskAssessmentContract is Ownable {
    mapping (address => RiskAssessmentData) public borrowerData;

    struct RiskAssessmentData {
        uint creditScore;
        uint income;
        uint debtToIncomeRatio;
        string personalityAssessment;
        bool isLowRisk;
        string[] riskMitigationGuidance;
    }

    event RiskAssessmentUpdated(address borrower, bool isLowRisk);

    function setBorrowerData(address _borrower, uint _creditScore, uint _income, uint _debtToIncomeRatio, string memory _personalityAssessment) public onlyOwner {
        RiskAssessmentData storage data = borrowerData[_borrower];
        data.creditScore = _creditScore;
        data.income = _income;
        data.debtToIncomeRatio = _debtToIncomeRatio;
        data.personalityAssessment = _personalityAssessment;
        bool isLowRisk = assessRisk(data);
        data.isLowRisk = isLowRisk;
        emit RiskAssessmentUpdated(_borrower, isLowRisk);
    }

    function getBorrowerData(address _borrower) public view returns (RiskAssessmentData memory) {
        return borrowerData[_borrower];
    }

    function assessRisk(RiskAssessmentData memory _data) internal returns (bool) {
        return _data.creditScore > 700;
    }

    function getRiskMitigationGuidance(address _borrower) public view returns (string[] memory) {
        return borrowerData[_borrower].riskMitigationGuidance;
    }

    function updateRiskMitigationGuidance(address _borrower, string[] memory _guidance) public onlyOwner {
        borrowerData[_borrower].riskMitigationGuidance = _guidance;
    }
}

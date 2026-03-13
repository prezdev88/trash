let bonificationThread;
let pointsThread;
let oldPoints;
let totalEarnedScore = 0;
let bonificationKeyWords = ["bonificación", "bono"];

function startBonificationThread() {
    console.clear();
    bonificationThreadBody();
    bonificationThread = setInterval(bonificationThreadBody, (1000));
}

function startPointsThread(){
    oldPoints = getBalancePoints();
    console.log("Current Points: " + oldPoints);
    pointsThread = setInterval(pointsThreadBody, (1000));
}

function bonificationThreadBody(){
    for(let button of getAllButtons()){
        if(isBonificationButtonExist(button)){
            button.click();
            break;
        }
    }
}

function pointsThreadBody(){
    let currentPoints = getBalancePoints();

    if(currentPoints != oldPoints){
        let differencePoints = currentPoints - oldPoints;
        console.log("Points has changed: " + " From " + oldPoints + " to " + currentPoints + " (+" + differencePoints + ")");
        oldPoints = currentPoints;
        accumalateScore(differencePoints);
        console.log("Total Earned Score: " + totalEarnedScore);
    }
}

function getBalancePoints(){
    for(let div of getAllDivs()){
        if(isBalanceStringDiv(div)){
            return Number.parseInt(div.children[0].innerHTML);
        }
    }
}

function getAllButtons(){
    return document.getElementsByTagName("button");
}

function getAllDivs(){
    return document.getElementsByTagName("div");
}

function isBonificationButtonExist(button){
    if(button.ariaLabel == undefined){
        return false;
    }

    for(let bonificationKeyWord of bonificationKeyWords){
        if(button.ariaLabel.includes(bonificationKeyWord)){
            return true;
        }
    }

    return false;
}

function isBalanceStringDiv(div){
    return div.dataset.testSelector != undefined && div.dataset.testSelector == "balance-string";
}

function accumalateScore(score){
    totalEarnedScore += score;
}

function stopInterval(){
    clearInterval(bonificationThread);
    console.log("bonificationThread stoped");
    clearInterval(pointsThread);
    console.log("pointsThread stoped");
}

startBonificationThread();
startPointsThread();
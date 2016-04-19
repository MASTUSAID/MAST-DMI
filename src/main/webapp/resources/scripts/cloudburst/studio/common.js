

function replaceString(mainString , regExp, replaceBy) {
    var result = mainString.replace(regExp, replaceBy);
    return result;
}


function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

function getUrlVar (name){
    return getUrlVars()[name];
}
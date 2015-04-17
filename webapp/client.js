/* SOA - Entry View
 * Martin Haidn
 * 2015-16-4
*/

var resObject = null;

/* Creates a new Responsive object
 ----------------------------------------------------------------------------*/
function createXMLHttpRequestObject() {
    var resObject = null;
    try {
        resObject = new ActiveXObject("Microsoft.XMLHTTP");
    } catch (Error) {
        try {
            resObject = new ActiveXObject("MSXML2.XMLHTTP");
        } catch (Error) {
            try {
                resObject = new XMLHttpRequest();
            } catch (Error) {
                alert ("Fehler beim Anlegen des XMLHttpRequest-Objekts");
            }
        }
    }
    return resObject;
}

var resObject = createXMLHttpRequestObject();


/* Closing function
------------------------------------------------------------------------------*/

window.document.onkeydown = function (e)
{
    if (!e){
        e = event;
    }
    if (e.keyCode == 27){
        lightbox_close();
    }
}

/* Entry view
------------------------------------------------------------------------------*/
function openEntry() {
    window.scrollTo(0,0);
    document.getElementById('light').style.display='block';
    document.getElementById('fade').style.display='block';
}

function closeEntry() {
    document.getElementById('light').style.display='none';
    document.getElementById('fade').style.display='none';
}

function setEntry(topic, text) {
    if (topic == '') {
        $('#e-header').text('New Entry');
    } else {
        $('#e-header').text(topic);
    }
    $('#topic').val(topic);
    $('#text').val(text);
}

function flushEntry() {
    $('#e-header').text('New Entry');
    $('#topic').val('');
    $('#text').val('');
}

/* Entry functions
------------------------------------------------------------------------------*/

function addEntry() {
    topic = $('#topic').val();
    text = $('textarea#text').val();

    if ($('#topic').val().lenght > 10 || $('textarea#text').val().length > 100) {
        alert("Topic is restricted to 10 characters and text to 100!");
    } else {
        resObject.open("GET", "index.php?action=add&topic=" + topic + "&text" + text, false);
        if (resObject.readyState == 4) {
            closeEntry();
            alert("Sucessfully created!");
        }
    }
}

function updateEntry() {
    setEntry("honk", "donl");
}

function removeEntry() {
    var id = $($)
}


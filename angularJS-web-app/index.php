<?php
define("KEY", ""); // key from sunlightfoundation.com
define("URL", "http://congress.api.sunlightfoundation.com/");
if (isset($_GET["api"])) {
    $api = $_GET["api"];
    if ($api === "legislators" | $api === "bills" | $api === "committees") {
        $url = URL . $api;
        switch ($api) {
            case "legislators":
                $url .= "?apikey=" . KEY;
                if (isset($_GET["bioguide_id"])) {
                    $url .= "&bioguide_id=" . $_GET["bioguide_id"];
                }
                $url .= "&per_page=all";
                break;
            case "bills":
                $url .= "?apikey=" . KEY;
                if (isset($_GET["bioguide_id"])) {
                    $url .= "&sponsor_id=" . $_GET["bioguide_id"];
                    $url .= "&per_page=5";
                } else {
                    $url .= "&per_page=50";
                    $url .= "&last_version.urls.pdf__exists=true";
                }
                if (isset($_GET["history_active"])) {
                    $url .= $_GET["history_active"] == "true" ? "&history.active=true" : "&history.active=false";    
                }
                if (isset($_GET["bill_id"])) {
                    $url .= "&bill_id=" . $_GET["bill_id"];
                }
                break;
            case "committees":
                $url .= "?apikey=" . KEY;
                if (isset($_GET["bioguide_id"])) {
                    $url .= "&member_ids=" . $_GET["bioguide_id"];
                    $url .= "&per_page=5";
                } else {
                    $url .= "&per_page=all";
                }
                break;
        }
        $json = file_get_contents($url);
        echo $json;
    }
}
?>
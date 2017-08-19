<!DOCTYPE html>
<html>
    <head>
        <title>Congress Info Search</title>
        <style type="text/css">
            body {
                text-align: center;
            }
            form {
                margin: 0 auto 20px auto;
                width: 350px;
                font-size: 18px;
            }
            form div {
                margin: 3px 0;
            }
            fieldset {
                margin: 0;
                padding: 0;
            }
            .title {
                float: left;
                width: 50%;
            }
            .botton {
                text-align: right;
                padding-right: 25px;
            }
            #keyword {
                width: 150px;
            }
            table {
                font-size: 18px;
                margin: auto;
                border-collapse: collapse;
            }
            th, td {
                width: 250px;
                border: 1px solid #000000;
            }
            #result {
                text-align: left;
            }
            #tab_detail {
                padding: 30px 100px;
                border: 1px solid #000000;
                border-collapse: separate;
            }
            #tab_detail td {
                width: 350px;
                text-align: left;
                border-width: 0;
            }
            #tab_detail img {
                display: block;
                margin: 0 auto 30px auto;                
            }
            .left {
                text-align: left;
                padding-left: 70px;
            }
            .wide {
                width: 500px;
            }
        </style>
    </head>
    <body>
        <?php
            define("KEY", ""); // key from sunlight foundation
            define("URL", "http://congress.api.sunlightfoundation.com/");
        
            $arr_keyword_label = array(
                "legislators" => "State/Representative*",
                "committees" => "Committee ID*",
                "bills" => "Bill ID*",
                "amendments" => "Amendment ID*"
            );

            $arr_state_name_to_code = array(
                "Alabama" => "AL", "Alaska" => "AK", "Arizona" => "AZ", "Arkansas" => "AR", "California" => "CA",
                "Colorado" => "CO", "Connecticut" => "CT", "Delaware" => "DE", "District Of Columbia" => "DC",
                "Florida" => "FL", "Georgia" => "GA", "Hawaii" => "HI", "Idaho" => "ID", "Illinois" => "IL",
                "Indiana" => "IN", "Iowa" => "IA", "Kansas" => "KS", "Kentucky" => "KY", "Louisiana" => "LA",
                "Maine" => "ME", "Maryland" => "MD", "Massachusetts" => "MA", "Michigan" => "MI", "Minnesota" => "MN",
                "Mississippi" => "MS", "Missouri" => "MO", "Montana" => "MT", "Nebraska" => "NE", "Nevada" => "NV",
                "New Hampshire" => "NH", "New Jersey" => "NJ", "New Mexico" => "NM", "New York" => "NY",
                "North Carolina" => "NC", "North Dakota" => "ND", "Ohio" => "OH", "Oklahoma" => "OK", "Oregon" => "OR",
                "Pennsylvania" => "PA", "Rhode Island" => "RI", "South Carolina" => "SC", "South Dakota" => "SD", 
                "Tennessee" => "TN", "Texas" => "TX", "Utah" => "UT", "Vermont" => "VT", "Virginia" => "VA", 
                "Washington" => "WA", "West Virginia" => "WV", "Wisconsin" => "WI", "Wyoming" => "WY"
            );
        
            function generate_url() {
                global $arr_state_name_to_code;
                $url = URL;
                if ($_POST["database"] === "legislators") {
                    $url .= $_POST["database"];
                    $url .= "?chamber=" . $_POST["chamber"];
                    $new_str = ucwords(strtolower(trim($_POST["keyword"])));
                    if (array_key_exists($new_str, $arr_state_name_to_code)) {
                        $url .= "&state=" . $arr_state_name_to_code[$new_str];
                    } elseif (str_word_count(trim($_POST["keyword"])) > 1) {
                        $url .= "&aliases=" . urlencode(ucwords(strtolower(trim($_POST["keyword"]))));
                    } else {
                        $url .= "&query=" . trim($_POST["keyword"]);
                    }
                } elseif ($_POST["database"] === "committees") {
                    $url .= $_POST["database"];
                    $url .= "?committee_id=" . strtoupper(trim($_POST["keyword"]));
                    $url .= "&chamber=" . $_POST["chamber"];
                } elseif ($_POST["database"] === "bills") {
                    $url .= $_POST["database"];
                    $url .= "?bill_id=" . strtolower(trim($_POST["keyword"]));
                    $url .= "&chamber=" . $_POST["chamber"];
                } elseif ($_POST["database"] === "amendments") {
                    $url .= $_POST["database"];
                    $url .= "?amendment_id=" . strtolower(trim($_POST["keyword"]));
                    $url .= "&chamber=" . $_POST["chamber"];
                }
                $url .= "&apikey=" . KEY;
                return $url;
            }
        
            function generate_url_detail() {
                global $arr_state_name_to_code;
                $url = URL;
                if ($_GET["database"] === "legislators") {
                    $url .= $_GET["database"];
                    $url .= "?chamber=" . $_GET["chamber"];
                    $new_str = ucwords(strtolower(trim($_GET["keyword"])));
                    if (array_key_exists($new_str, $arr_state_name_to_code)) {
                        $url .= "&state=" . $arr_state_name_to_code[$new_str];
                    } elseif (str_word_count(trim($_GET["keyword"])) > 1) {
                        $url .= "&aliases=" . urlencode(ucwords(strtolower(trim($_GET["keyword"]))));
                    } else {
                        $url .= "&query=" . trim($_GET["keyword"]);
                    }
                    $url .= "&bioguide_id=". $_GET["bioguide_id"];
                } elseif ($_GET["database"] === "bills") {
                    $url .= $_GET["database"];
                    $url .= "?bill_id=" . strtolower(trim($_GET["keyword"]));
                    $url .= "&chamber=" . $_GET["chamber"];
                } 
                $url .= "&apikey=" . KEY;
                return $url;
            }
        
            function create_table_legislators($arr) {
                echo "<table><tr><th>Name</th><th>State</th><th>Chamber</th><th>Details</th></tr>";
                for ($i = 0; $i < count($arr["results"]); $i++) {
                    echo "<tr>";
                    echo "<td class='left'>";
                    echo is_null($arr["results"][$i]["first_name"]) ? "NA" : $arr["results"][$i]["first_name"];
                    echo " ";
                    echo is_null($arr["results"][$i]["last_name"]) ? "NA" : $arr["results"][$i]["last_name"];
                    echo "</td>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["state_name"]) ? "NA" : $arr["results"][$i]["state_name"];
                    echo "</td>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["chamber"]) ? "NA" : $arr["results"][$i]["chamber"];
                    echo "</td>";
                    echo "<td><a href='' id='" . $arr["results"][$i]["bioguide_id"] . "' ";
                    echo "onclick='return set_href(this);'>View Details</a></td>";
                    echo "</tr>";
                }               
                echo "</table>";
            }
        
            function create_table_committees($arr) {
                echo "<table><tr><th>Committee ID</th><th>Committee Name</th><th>Chamber</th></tr>";
                for ($i = 0; $i < count($arr["results"]); $i++) {
                    echo "<tr>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["committee_id"]) ? "NA" : $arr["results"][$i]["committee_id"];
                    echo "</td>";
                    echo "<td class='wide'>";
                    echo is_null($arr["results"][$i]["name"]) ? "NA" : $arr["results"][$i]["name"];
                    echo "</td>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["chamber"]) ? "NA" : $arr["results"][$i]["chamber"];
                    echo "</td>";
                    echo "</tr>";
                }               
                echo "</table>";
            }

            function create_table_bills($arr) {
                echo "<table><tr><th>Bill ID</th><th>Short Title</th><th>Chamber</th><th>Details</th></tr>";
                for ($i = 0; $i < count($arr["results"]); $i++) {
                    echo "<tr>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["bill_id"]) ? "NA" : $arr["results"][$i]["bill_id"];
                    echo "</td>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["short_title"]) ? "NA" : $arr["results"][$i]["short_title"];
                    echo "</td>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["chamber"]) ? "NA" : $arr["results"][$i]["chamber"];
                    echo "</td>";
                    echo "<td><a href='' id='" . $arr["results"][$i]["bill_id"] . "' ";
                    echo "onclick='return set_href(this);'>View Details</a></td>";
                    echo "</tr>";
                }               
                echo "</table>";
            }

            function create_table_amendments($arr) {
                echo "<table><tr><th>Amendment ID</th><th>Amendment Type</th><th>Chamber</th><th>Introduced on</th></tr>";
                for ($i = 0; $i < count($arr["results"]); $i++) {
                    echo "<tr>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["amendment_id"]) ? "NA" : $arr["results"][$i]["amendment_id"];
                    echo "</td>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["amendment_type"]) ? "NA" : $arr["results"][$i]["amendment_type"];
                    echo "</td>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["chamber"]) ? "NA" : $arr["results"][$i]["chamber"];
                    echo "</td>";
                    echo "<td>";
                    echo is_null($arr["results"][$i]["introduced_on"]) ? "NA" : $arr["results"][$i]["introduced_on"];
                    echo "</td>";
                    echo "</tr>";
                }               
                echo "</table>";
            }
        
            function create_table_legislators_detail($arr) {
                echo "<table id='tab_detail'>";
                echo "<tr><td colspan='2'><img src='https://theunitedstates.io/images/congress/225x275/";
                echo $arr["results"][0]["bioguide_id"] . ".jpg' /></td></tr>";
                echo "<tr><td>Full Name</td><td>";
                echo $arr["results"][0]["title"] . " " . $arr["results"][0]["first_name"] . " " . $arr["results"][0]["last_name"];
                echo "</td></tr>";
                echo "<tr><td>Term Ends on</td><td>";
                echo is_null($arr["results"][0]["term_end"]) ? "NA" : $arr["results"][0]["term_end"];
                echo "</td></tr>";
                echo "<tr><td>Website</td><td><a href='" . $arr["results"][0]["website"] . "' target='_blank'>";
                echo is_null($arr["results"][0]["website"]) ? "NA" : $arr["results"][0]["website"];
                echo "</a></td></tr>";
                echo "<tr><td>Office</td><td>";
                echo is_null($arr["results"][0]["office"]) ? "NA" : $arr["results"][0]["office"];
                echo "</td></tr>";
                echo "<tr><td>Facebook</td><td>";
                echo "<a href='https://www.facebook.com/" . $arr["results"][0]["facebook_id"] . "' target='_blank'>";
                echo is_null($arr["results"][0]["facebook_id"]) ? "NA" : $arr["results"][0]["first_name"] . " " . $arr["results"][0]["last_name"];
                echo "</a></td></tr>";
                echo "<tr><td>Twitter</td><td>";
                echo "<a href='https://twitter.com/" . $arr["results"][0]["twitter_id"] . "' target='_blank'>";
                echo is_null($arr["results"][0]["twitter_id"]) ? "NA" : $arr["results"][0]["first_name"] . " " . $arr["results"][0]["last_name"];
                echo "</a></td></tr>";                
                echo "</table>";
            }
        
            function create_table_bills_detail($arr) {
                echo "<table id='tab_detail'>";
                echo "<tr><td>Bill ID</td><td>";
                echo is_null($arr["results"][0]["bill_id"]) ? "NA" : $arr["results"][0]["bill_id"];
                echo "</td></tr>";
                echo "<tr><td>Bill Title</td><td>";
                echo is_null($arr["results"][0]["short_title"]) ? "NA" : $arr["results"][0]["short_title"];
                echo "</td></tr>";
                echo "<tr><td>Sponsor</td><td>";
                echo $arr["results"][0]["sponsor"]["title"] . " " . $arr["results"][0]["sponsor"]["first_name"] . " " . $arr["results"][0]["sponsor"]["first_name"];
                echo "</td></tr>";
                echo "<tr><td>Introduced On</td><td>";
                echo is_null($arr["results"][0]["introduced_on"]) ? "NA" : $arr["results"][0]["introduced_on"];
                echo "</td></tr>";
                echo "<tr><td>Last action with date</td><td>" . $arr["results"][0]["last_version"]["version_name"];
                echo ", " . $arr["results"][0]["last_action_at"] . "</td></tr>";
                echo "<tr><td>Bill URL</td><td><a href='" . $arr["results"][0]["last_version"]["urls"]["pdf"];
                echo "' target='_blank'>";
                echo is_null($arr["results"][0]["short_title"]) ? $arr["results"][0]["bill_id"] : $arr["results"][0]["short_title"] . "</td></tr>";                
                echo "</table>";
            }
        ?>
        <h1>Congress Information Search</h1>
        <form method="post" action="http://localhost/congress.php" onsubmit="return check_form(this);" id="my_form">
            <fieldset>
                <div>
                    <label for="database" class="title">Congress Database</label>
                    <select name="database" id="database">
                        <option value="unselected" disabled
                            <?php
                                if (!isset($_POST["database"]) && !isset($_GET["database"])) { 
                                    echo "selected";
                                }
                            ?>
                        >Select your option</option>
                        <option value="legislators" 
                            <?php 
                                if ((isset($_POST["database"]) && $_POST["database"] === "legislators") || 
                                    (isset($_GET["database"]) && $_GET["database"] === "legislators")) {
                                    echo "selected";
                                }
                            ?>
                        >Legislators</option>
                        <option value="committees" 
                            <?php 
                                if ((isset($_POST["database"]) && $_POST["database"] === "committees") || 
                                    (isset($_GET["database"]) && $_GET["database"] === "committees")) {
                                    echo "selected";
                                } 
                            ?>
                        >Committees</option>
                        <option value="bills" 
                            <?php 
                                if ((isset($_POST["database"]) && $_POST["database"] === "bills") || 
                                    (isset($_GET["database"]) && $_GET["database"] === "bills")) {
                                    echo "selected"; 
                                }
                            ?>
                        >Bills</option>
                        <option value="amendments" 
                            <?php 
                                if ((isset($_POST["database"]) && $_POST["database"] === "amendments") || 
                                    (isset($_GET["database"]) && $_GET["database"] === "amendments")) {
                                    echo "selected";
                                }
                            ?>
                        >Amendments</option>
                    </select>
                </div>
                <div>
                    <label for="chamber" class="title">Chamber</label>
                    <span id="chamber">
                        <input type="radio" name="chamber" value="senate" id="senate"
                            <?php
                                if (!isset($_POST["chamber"]) && !isset($_GET["chamber"])) { 
                                    echo "checked"; 
                                } elseif (isset($_POST["chamber"]) && $_POST["chamber"] === "senate") { 
                                    echo "checked"; 
                                } elseif (isset($_GET["chamber"]) && $_GET["chamber"] === "senate") { 
                                    echo "checked"; 
                                }
                            ?>
                        />
                        <label for="senate">Senate</label>
                        <input type="radio" name="chamber" value="house" id="house" 
                            <?php
                                if (isset($_POST["chamber"]) && $_POST["chamber"] === "house") { 
                                    echo "checked"; 
                                } elseif (isset($_GET["chamber"]) && $_GET["chamber"] === "house") { 
                                    echo "checked"; 
                                }
                            ?>
                        />
                        <label for="house">House</label>
                    </span>
                </div>
                <div>
                    <label for="keyword" class="title" id="keyword_label">
                        <?php
                            if (isset($_POST["database"])) { 
                                echo $arr_keyword_label[$_POST["database"]]; 
                            } elseif (isset($_GET["database"])) { 
                                echo $arr_keyword_label[$_GET["database"]]; 
                            } else { echo "Keyword*"; }
                        ?>
                    </label>
                    <input type="text" name="keyword" id="keyword" 
                           value="<?php
                            if (isset($_POST["keyword"])) { 
                                echo $_POST["keyword"]; 
                            } elseif (isset($_GET["keyword"])) { 
                                echo $_GET["keyword"]; 
                            } 
                        ?>" />
                </div>
                <div class="botton">
                    <input type="submit" name="submit" value="Search" />
                    <input type="reset" value="Clear" id="reset_btn" />
                </div>
                <div>
                    <a href="http://sunlightfoundation.com" target="_blank">Powered by Sunlight Foundation</a>
                </div>
            </fieldset>
        </form>
        <?php 
            if (isset($_POST["submit"])) {                
                $url = generate_url();
                $json = file_get_contents($url);
                $arr = json_decode($json, true);
                if (count($arr["results"]) === 0) {
                    echo "<h2>The API returned zero results for the request.</h2><br />";
                } else if ($_POST["database"] === "legislators") {
                    create_table_legislators($arr);
                } else if ($_POST["database"] === "committees") {
                    create_table_committees($arr);
                } else if ($_POST["database"] === "bills") {
                    create_table_bills($arr);
                } else if ($_POST["database"] === "amendments") {
                    create_table_amendments($arr);
                }                
            } elseif (isset($_GET["bioguide_id"])) {                
                $url = generate_url_detail();
                $json = file_get_contents($url);
                $arr = json_decode($json, true);
                if (count($arr["results"]) === 0) {
                    echo "<h2>The API returned zero results for the request.</h2><br />";
                } else if ($_GET["database"] === "legislators") {
                    create_table_legislators_detail($arr);
                } else if ($_GET["database"] === "bills") {
                    create_table_bills_detail($arr);
                }
            }
        ?>
        <script type="text/javascript">
            function isAllWhiteSpaceStr(str) {
              return !(/[^\t\n\r ]/.test(str));
            }
            
            function change_keyword_label(e) {
                var my_label = document.getElementById("keyword_label");
                var arr = ["Keyword*",
                           "State/Representative*",
                           "Committee ID*",
                           "Bill ID*",
                           "Amendment ID*"];
                my_label.firstChild.nodeValue = arr[e.target.selectedIndex];
            }
            var select_list = document.getElementById("database");
            select_list.addEventListener("change", change_keyword_label);
            
            function reset() {
                var select_list = document.getElementById("database");
                var curr_select_index = select_list.selectedIndex;
                select_list.options[curr_select_index].removeAttribute("selected");
                select_list.options[0].setAttribute("selected", "selected");
                
                var house = document.getElementById("house");
                if (house.hasAttribute("checked")) {
                    house.removeAttribute("checked");
                    document.getElementById("senate").setAttribute("checked", "checked");
                }                
                
                var keyword_label = document.getElementById("keyword_label");
                keyword_label.firstChild.nodeValue = "Keyword*";
                
                var keyword_text = document.getElementById("keyword");
                keyword_text.setAttribute("value", "");
            }
            var reset_btn = document.getElementById("reset_btn");
            reset_btn.addEventListener("click", reset);
            
            function check_form(form) {
                var str = "";
                if (form.database.selectedIndex === 0) {
                    str += "Congress database";
                }
                if (form.keyword.value.length === 0 || isAllWhiteSpaceStr(form.keyword.value)) {
                    if (str.length > 0) {
                        str += ", ";
                    }
                    str += "keyword";
                }
                if (str.length > 0) {
                    var prefix = "Please enter the following missing information: ";
                    alert(prefix + str);
                    return false;
                }
                return true;
            }
            
            function set_href(a) {
                var my_form = document.getElementById("my_form");
                var url = my_form.getAttribute("action");
                var db = my_form.database.options[my_form.database.selectedIndex].value;
                var chamber = my_form.chamber.value;
                var keyword = my_form.keyword.value;
                var bioguide_id = a.getAttribute("id");
                url += "?database=" + db + "&chamber=" + chamber + "&keyword=" + keyword + "&bioguide_id=" + bioguide_id;
                a.setAttribute("href", url);
                return true;
            }
        </script>        
    </body>
</html>
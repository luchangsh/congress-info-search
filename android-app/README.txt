An Android app for congress information search (including legislators/bills/committees search and my favorites function).
The backend server is using PHP, Apache server, Amazon Web Services, and Sunlight Congress APIs.

#============================ File Description =============================#

\main\AndroidManifest.xml           --> AndroidManifest.xml

\main\res\                          --> Resources XML files

\main\java\com\example\cs571hw9

	MainActivity.java               --> Main Activity

	LegislatorsFragment.java        --> Legislator Fragment
	LegislatorsPagerAdapter.java    --> Pager Adapter for Legislator Fragment
	LegislatorsStateFragment.java	--> By State Tab
	LegislatorsHouseFragment.java	--> House Tab
	LegislatorsSenateFragment.java	--> Senate Tab
	LegislatorsListAdapter.java		--> List Adapter for Legislator Fragment

	BillsFragment.java			    --> Bills Fragment
	BillsPagerAdapter.java		    --> Pager Adapter for Bills Fragment
	BillsActiveFragment.java		--> Active Tab
	BillsNewFragment.java		    --> New Tab
	BillsListAdapter.java		    --> List Adapter for Bills Fragment

	CommitteesFragment.java		    --> Committees Fragment
	CommitteesPagerAdapter.java		--> Pager Adapter for Committees Fragment
	CommitteesHouseFragment.java	--> House Tab
	CommitteesSenateFragment.java	--> Senate Tab
	CommitteesJointFragment.java	--> Joint Tab
	CommitteesListAdapter.java		--> List Adapter for Committees Fragment

	FavoritesFragment.java		    --> Favorites Fragment
	FavoritesPagerAdapter.java		--> Pager Adapter for Favorites Fragment
	FavoritesLegislatorsFragment.java	--> Legislators Tab
	FavoritesBillsFragment.java		--> Bills Tab
	FavoritesCommitteesFragment.java	--> Committees Tab

	DetailsLegislatorsActivity.java	--> Legislators Info
	DetailsBillsActivity.java		--> Bills Info
	DetailsCommitteesActivity.java	--> Committees Info

	AboutActivity.java			    --> About Me Activity

	HttpManager.java			    --> For Http Request 
	RequestPackage.java		        --> Http Request Package

	MyUtility.java			        --> Utility functions for processing string and date

\main\java\com\example\cs571hw9\model

	Legislator.java			        --> Legislator Object
	Bill.java				        --> Bill Object
	Committee.java			        --> Committee Object

\main\java\com\example\cs571hw9\parser

	LegislatorJSONParser.java		--> Parse JSON string to Legislator Object
	BillJSONParser.java			    --> Parse JSON string to Bill Object
	CommitteeJSONParser.java		--> Parse JSON string to Committee Object






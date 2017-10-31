/*
 * This section is to if we want to have the clients configuration in mysql
 */

INSERT INTO oauth_client_details
	(client_id, resource_ids,client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	("ui1",null, "ui1-secret", "ui1.read",
	"authorization_code,refresh_token,password", null, "ROLE_TRUSTED_CLIENT", 36000, 36000, null, "false");
INSERT INTO oauth_client_details
	(client_id,resource_ids, client_secret, scope, authorized_grant_types,
	web_server_redirect_uri, authorities, access_token_validity,
	refresh_token_validity, additional_information, autoapprove)
VALUES
	("ui2", null,"ui2-secret", "ui2.read,ui2.write",
	"authorization_code,refresh_token", null, "ROLE_TRUSTED_CLIENT", 36000, 36000, null, "false");

--//REPORT DI TEST
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'RTC1','Conferenze','REPORT','conferenzaTestReport','report di test','PRIVATE');
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'ODTC1','Conferenze','REPORT','conferenzaTestReport','report di test','PRIVATE');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(2,'title','String','custom title');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(2,'condizione1','String','tipologiaConferenzaSpec.semplificata');

--//REPORT TIPOLOGIE CONFERENZA
--//1 - tipologiaConferenzaSpec.semplificata
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'RC1','Conferenze semplificata','REPORT','conferenzaTipoReport','lista delle conferenze semplificate','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(3,'title','String','Conferenze Semplificate');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(3,'condizione1','String','tipologiaConferenzaSpec.semplificata');
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'ODC1','Conferenze semplificata','OPENDATA','conferenzaTipoReport','Il seguente open data restituisce l&apos;elenco di tutte le conferenze di tipo semplificato','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(4,'title','String','Conferenze Semplificate');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(4,'condizione1','String','tipologiaConferenzaSpec.semplificata');

--"2"	"tipologiaConferenzaSpec.simultanea"
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'RC2','Conferenze simultanea','REPORT','conferenzaTipoReport','lista delle conferenze simultanee','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(5,'title','String','Conferenze Simultanee');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(5,'condizione1','String','tipologiaConferenzaSpec.simultanea');
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'ODC2','Conferenze simultanea','OPENDATA','conferenzaTipoReport','Il seguente open data restituisce l&apos;elenco di tutte le conferenze di tipo simultanee','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(6,'title','String','Conferenze Simultanee');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(6,'condizione1','String','tipologiaConferenzaSpec.simultanea');

--"3"	"tipologiaConferenzaSpec.regionale"
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'RC3','Conferenze regionale','REPORT','conferenzaTipoReport','lista delle conferenze regionali','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(7,'title','String','Conferenze regionali');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(7,'condizione1','String','tipologiaConferenzaSpec.regionale');
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'ODC3','Conferenze regionale','OPENDATA','conferenzaTipoReport','Il seguente open data restituisce l&apos;elenco di tutte le conferenze di tipo regionali','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(8,'title','String','Conferenze regionali');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(8,'condizione1','String','tipologiaConferenzaSpec.regionale');


--"4"	"tipologiaConferenzaSpec.BULDecisoriaSimultanea"
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'RC4','Conferenze BULDecisoriaSimultanea','REPORT','conferenzaTipoReport','lista delle conferenze BUL Decisorie Simultanee','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(9,'title','String','BUL Decisorie Simultanee');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(9,'condizione1','String','tipologiaConferenzaSpec.BULDecisoriaSimultanea');
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'ODC4','Conferenze BULDecisoriaSimultanea','OPENDATA','conferenzaTipoReport','Il seguente open data restituisce l&apos;elenco di tutte le conferenze di tipo BUL Decisorie Simultanee','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(10,'title','String','Conferenze BUL Decisorie Simultanee');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(10,'condizione1','String','tipologiaConferenzaSpec.BULDecisoriaSimultanea');

--"5"	"tipologiaConferenzaSpec.BUListruttoriaSimultanea"
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'RC5','Conferenze BUListruttoriaSimultanea','REPORT','conferenzaTipoReport','lista delle conferenze BUL Istruttoria Simultanee','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(11,'title','String','BUL Istruttoria Simultanee');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(11,'condizione1','String','tipologiaConferenzaSpec.BUListruttoriaSimultanea');
insert into report (codice,descrizione,tiporeport,modello,note,visibilita)values(
'ODC5','Conferenze BUListruttoriaSimultanea','OPENDATA','conferenzaTipoReport','Il seguente open data restituisce l&apos;elenco di tutte le conferenze di tipo BUL Istruttoria Simultanee','PUBLIC');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(12,'title','String','Conferenze BUL Istruttoria Simultanee');
insert into report_parameter (fk_report,parametronome,parametrotipo,parametrodefault)values(12,'condizione1','String','tipologiaConferenzaSpec.BUListruttoriaSimultanea');








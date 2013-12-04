<%-- 
    Document   : searchBox
    Created on : Aug 21, 2011, 12:29:50 PM
    Author     : hongcao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib  prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="xchars" uri="http://www.ebi.ac.uk/xchars"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!--<link href="resources/css/search.css" type="text/css" rel="stylesheet" />
<script src="resources/javascript/search.js" type="text/javascript"></script>-->
<script>
    function showCard(tabId){
        var s = tabId.split('-tab-');
        var cardsId = '#' + s[0];
        var tabsId = '#' + s[0] + '-tabs';
        var theCardId = '#' + s[0] + '-' + s[1];
        var theTabId = '#' + s[0] + '-tab-' + s[1];
        $(cardsId).children().css('display', 'none');
        $(tabsId).children().removeClass('selected');
        $(theCardId).css('display', 'block');
        $(theTabId).addClass('selected');
    }

    function submitKeywordForm(text){
        $('#search-keyword-text').val(text);
        $('#search-keyword-submit').click();
    }
    /**
     * sequence examples submit
     */
    function submitSequenceForm(text) {
        
         $('#search-sequence-text').val(text);
        $('#search-sequence-submit').click();
    }
</script>

<ul id="search-tabs"
    style="width: 70em; margin-left: auto; margin-right: auto;">
    <li id="search-tab-keyword"
        class="searchTab ${empty searchModel.searchparams.type or
                           searchModel.searchparams.type eq 'KEYWORD'? 'selected':'' }"
        onclick="showCard(this.id);">
        <spring:message code="label.search.tab.keyword"/>
    </li>
    <li id="search-tab-sequence"
        class="searchTab ${searchModel.searchparams.type eq 'SEQUENCE'?
                           'selected':'' }"
        onclick="showCard(this.id);">
        <spring:message code="label.search.tab.sequence"/>
    </li>
    <li id="search-tab-compound"
        class="searchTab ${searchModel.searchparams.type eq 'COMPOUND'?
            'selected':'' }"
        onclick="showCard(this.id);">
        <spring:message code="label.search.tab.compound"/>
    </li>
</ul>

<form:form id="searchForm" modelAttribute="searchModel"
           action="${pageContext.request.contextPath}/search" method="POST">
    <form:hidden path="searchparams.previoustext" />

    <div id="search" style="min-height: 20ex;">
        <div id="search-keyword" class="searchBackground searchTabContent"
             style="display: ${empty searchModel.searchparams.type or
                CsearchModel.searchparams.type eq 'KEYWORD'? 'block':'none' };">
             <form:input id="search-keyword-text" path="searchparams.text"
                         cssClass="field" />
             <button id="search-keyword-submit" type="submit"
                     name="searchparams.type" value="KEYWORD"
                     class="searchButton">Search</button>
             <br />
             <spring:message code="label.search.example" />
              <a href="/enzymeportal/search?searchparams.type=KEYWORD&searchparams.previoustext=&searchparams.start=0&searchparams.text=sildenafil">sildenafil</a>,
                <a href="/enzymeportal/search?searchparams.type=KEYWORD&searchparams.previoustext=&searchparams.start=0&searchparams.text=Insulin+receptor">Insulin receptor</a>,
                <a href="/enzymeportal/search?searchparams.type=KEYWORD&searchparams.previoustext=&searchparams.start=0&searchparams.text=Ceramide+glucosyltransferase">Ceramide glucosyltransferase</a>,
                <a href="/enzymeportal/search?searchparams.type=KEYWORD&searchparams.previoustext=&searchparams.start=0&searchparams.text=Phenylalanine-4-hydroxylase">Phenylalanine-4-hydroxylase</a>,
                <a href="/enzymeportal/search?searchparams.type=KEYWORD&searchparams.previoustext=&searchparams.start=0&searchparams.text=Cytochrome+P450+3A4">Cytochrome P450 3A4</a>,
                <a href="/enzymeportal/search?searchparams.type=KEYWORD&searchparams.previoustext=&searchparams.start=0&searchparams.text=CFTR">CFTR</a>,
                <a href="/enzymeportal/search?searchparams.type=KEYWORD&searchparams.previoustext=&searchparams.start=0&searchparams.text=Q13423">Q13423</a>,
                <a href="/enzymeportal/search?searchparams.type=KEYWORD&searchparams.previoustext=&searchparams.start=0&searchparams.text=REACT_1400.4">REACT_1400.4</a>
<%--
             <a class="formSubmit"
                onclick="submitKeywordForm('sildenafil')">sildenafil</a>,
             <a class="formSubmit"
                onclick="submitKeywordForm('Insulin receptor')">Insulin receptor</a>,
             <a class="formSubmit"
                onclick="submitKeywordForm('Ceramide glucosyltransferase')">Ceramide
                 glucosyltransferase</a>,
             <a class="formSubmit"
                onclick="submitKeywordForm('Phenylalanine-4-hydroxylase')"
                >Phenylalanine-4-hydroxylase</a>,
             <a class="formSubmit"
                onclick="submitKeywordForm('Cytochrome P450 3A4')">Cytochrome
                 P450 3A4</a>,
             <a class="formSubmit"
                onclick="submitKeywordForm('CFTR')">CFTR</a>,
             <a class="formSubmit"
                onclick="submitKeywordForm('Q13423')">Q13423</a>,
             <a class="formSubmit"
                onclick="submitKeywordForm('REACT_1400.4')">REACT_1400.4</a>
--%>
             <hr/>
             <br/>
             <section>
                 <h3 style="text-align: left">Keyword Search guideline</h3>
                 <p>To use the keyword search, simply enter a search term and click on the search button. For example you can enter <a class="formSubmit"
                                                                                                                                       onclick="submitKeywordForm('sildenafil')">sildenafil</a> as a keyword in the search field provided.</p>
             </section>
        </div>

        <div id="search-sequence" class="searchBackground searchTabContent"
             style="display: ${searchModel.searchparams.type eq 'SEQUENCE'?
                        'block':'none' };">
            <form:textarea id="search-sequence-text" path="searchparams.sequence" cols="80" rows="5"
                            title="Enter a protein sequence to search" />
             <button id="search-sequence-submit" type="submit"
                     name="searchparams.type" value="SEQUENCE"
                     class="searchButton">Sequence Search</button>
             <br/>
             <section>
                 <h3 style="text-align: left">Sequence Search guideline</h3>

                 <p>In order to use the sequence search, the search term must be in FASTA format as shown in the example below:</p>
                 <pre style="color: #996a44; background-color:whitesmoke ">
<a style="border-bottom-style: none" href="/enzymeportal/search?searchparams.type=SEQUENCE&searchparams.sequence=MQRSPLEKASVVSKLFFSWTRPILRKGYRQRLELSDIYQIPSVDSADNLSEKLEREWDRE
                LASKKNPKLINALRRCFFWRFMFYGIFLYLGEVTKAVQPLLLGRIIASYDPDNKEERSIA
                IYLGIGLCLLFIVRTLLLHPAIFGLHHIGMQMRIAMFSLIYKKTLKLSSRVLDKISIGQL
                VSLLSNNLNKFDEGLALAHFVWIAPLQVALLMGLIWELLQASAFCGLGFLIVLALFQAGL
                GRMMMKYRDQRAGKISERLVITSEMIENIQSVKAYCWEEAMEKMIENLRQTELKLTRKAA
                YVRYFNSSAFFFSGFFVVFLSVLPYALIKGIILRKIFTTISFCIVLRMAVTRQFPWAVQT
                WYDSLGAINKIQDFLQKQEYKTLEYNLTTTEVVMENVTAFWEEGFGELFEKAKQNNNNRK">
&gt;sp|P13569|CFTR_HUMAN Cystic fibrosis transmembrane conductance regulator OS=Homo sapiens GN=CFTR PE=1 SV=3
MQRSPLEKASVVSKLFFSWTRPILRKGYRQRLELSDIYQIPSVDSADNLSEKLEREWDRE
LASKKNPKLINALRRCFFWRFMFYGIFLYLGEVTKAVQPLLLGRIIASYDPDNKEERSIA
IYLGIGLCLLFIVRTLLLHPAIFGLHHIGMQMRIAMFSLIYKKTLKLSSRVLDKISIGQL
VSLLSNNLNKFDEGLALAHFVWIAPLQVALLMGLIWELLQASAFCGLGFLIVLALFQAGL
GRMMMKYRDQRAGKISERLVITSEMIENIQSVKAYCWEEAMEKMIENLRQTELKLTRKAA
YVRYFNSSAFFFSGFFVVFLSVLPYALIKGIILRKIFTTISFCIVLRMAVTRQFPWAVQT
WYDSLGAINKIQDFLQKQEYKTLEYNLTTTEVVMENVTAFWEEGFGELFEKAKQNNNNRK</a>
<%--
    <a style="border-bottom-style: none" class="formSubmit"
                onclick="submitSequenceForm('MQRSPLEKASVVSKLFFSWTRPILRKGYRQRLELSDIYQIPSVDSADNLSEKLEREWDRELASKKNPKLINALRRCFFWRFMFYGIFLYLGEVTKAVQPLLLGRIIASYDPDNKEERSIAIYLGIGLCLLFIVRTLLLHPAIFGLHHIGMQMRIAMFSLIYKKTLKLSSRVLDKISIGQLVSLLSNNLNKFDEGLALAHFVWIAPLQVALLMGLIWELLQASAFCGLGFLIVLALFQAGLGRMMMKYRDQRAGKISERLVITSEMIENIQSVKAYCWEEAMEKMIENLRQTELKLTRKAAYVRYFNSSAFFFSGFFVVFLSVLPYALIKGIILRKIFTTISFCIVLRMAVTRQFPWAVQTWYDSLGAINKIQDFLQKQEYKTLEYNLTTTEVVMENVTAFWEEGFGELFEKAKQNNNNRK')"> 
        >sp|P13569|CFTR_HUMAN Cystic fibrosis transmembrane conductance regulator OS=Homo sapiens GN=CFTR PE=1 SV=3
                MQRSPLEKASVVSKLFFSWTRPILRKGYRQRLELSDIYQIPSVDSADNLSEKLEREWDRE
                LASKKNPKLINALRRCFFWRFMFYGIFLYLGEVTKAVQPLLLGRIIASYDPDNKEERSIA
                IYLGIGLCLLFIVRTLLLHPAIFGLHHIGMQMRIAMFSLIYKKTLKLSSRVLDKISIGQL
                VSLLSNNLNKFDEGLALAHFVWIAPLQVALLMGLIWELLQASAFCGLGFLIVLALFQAGL
                GRMMMKYRDQRAGKISERLVITSEMIENIQSVKAYCWEEAMEKMIENLRQTELKLTRKAA
                YVRYFNSSAFFFSGFFVVFLSVLPYALIKGIILRKIFTTISFCIVLRMAVTRQFPWAVQT
                WYDSLGAINKIQDFLQKQEYKTLEYNLTTTEVVMENVTAFWEEGFGELFEKAKQNNNNRK

                </a>
--%>
</pre>
                 <p>However, the header <pre style="color: #996a44; background-color:white;">  &gt;sp|P13569|CFTR_HUMAN Cystic fibrosis transmembrane conductance regulator OS=Homo sapiens GN=CFTR PE=1 SV=3</pre> as shown in the above example is optional.</p>
                 <p><a style="border-bottom-style: none" href="http://www.ncbi.nlm.nih.gov/BLAST/blastcgihelp.shtml"><h6 style="color: mediumblue; text-decoration: underline;">Learn More About FASTA Format</h6></a></p>
<%--
                 <a   name="fasta" id="fasta"></a>
                 <ul>
                   <li>This format contains a single header line providing the sequence 
                     name, and optionally a description, followed by lines of sequence data.</li>
                   <li>Sequences in FASTA formatted files are preceded by a line 
                     starting with a &quot; &gt;&quot; symbol.</li>
                   <li>The first word on this line is the name of the sequence. The rest 
                     of the line is a description of the sequence.</li>
                 </ul>
                 <div class="commentsbox">
                   <pre style="color: #996a44; background-color:white;"><span class="insidecommentsbox">
                        Term 	Entry Name 	Molecule Type 	Gene Name 	Sequence Length
                        e.g. 	FOSB_MOUSE 	Protein 	fosB 	338 bp
                 </span></pre>
                 </div>
                 <ul>
                   <li>The remaining lines contain the sequence itself, usually formated 
                     to 60 characters per line.</li>
                   <li>Depending on the application blank lines in a FASTA file are 
                     ignored or treated as terminating the sequence </li>
                   <li>Depending on the application spaces or other non-sequence symbols 
                     (dashes, underscores, periods) in a sequence are either ignored or 
                     treated as gaps.</li>
                   <li>FASTA files containing multiple sequences are just the same, with 
                     one sequence listed right after another. This format is accepted for 
                     many multiple sequence alignment programs.</li>
                 </ul>
--%>
             </section>
        </div>
        
        <div id="search-compound" class="searchBackground searchTabContent"
            style="display: ${searchModel.searchparams.type eq 'COMPOUND'?
                'block':'none' };">
            <iframe src="${searchConfig.structureSearchUrl}"
                style="border: none; margin: 0px; overflow: auto;"
                width="100%" height="1200ex"></iframe>
        </div>
    </div>

</form:form>
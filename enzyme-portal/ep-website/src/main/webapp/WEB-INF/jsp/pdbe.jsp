<%--
    Document   : pdbe
    Created on : Jul 10, 2018, 4:34:44 PM
    Author     : Joseph
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!--<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.4.3/css/foundation.min.css">-->

<div class="summary structure" id="${proteinStructure.id}">
    <div class="summary">
        <div class="large-12 columns row structure-view">
          <div class="large-6 columns">
            <div class="image">
              <div class="main_link">
                  <a rel="external" target="_blank" href="http://www.ebi.ac.uk/pdbe-srv/view/entry/${proteinStructure.id}/summary">View in PDBe</a>
              </div>
                <a rel="external" title="Click for an interactive viewer" target="_blank"
                   href="http://www.ebi.ac.uk/pdbe-srv/view/entry/${proteinStructure.id}/openastex"><img
                        alt="Ribbon structure of ${proteinStructure.id}"
                        src="http://www.ebi.ac.uk/pdbe/static/entry/${fn:toLowerCase(proteinStructure.id)}_deposited_chain_front_image-800x800.png"/>
                    <span class="caption">Ribbon structure of ${proteinStructure.id}</span>
                </a>
            </div>
          </div>

          <div class="large-6 columns">
            <div>
              <span>Method: </span>
              <c:forEach var="method" items="${proteinStructure.experimentMethod}">
                <span class="note_${vs.index}">${method}</span>
              </c:forEach>
            </div>
            <div>
              <span>Experiment: </span>
              <span class="note_${vs.index}">Resolution : ${proteinStructure.resolution}&#194;</span>
            </div>
          </div>
        </div>

    </div>
    <c:if test="${not empty proteinStructure.cofactors}">
        <dl>
            <c:choose>
                <c:when test="${fn:length(proteinStructure.cofactors) == 1}">
                    <dt> <b>Cofactor:</b></dt>
                </c:when>
                <c:otherwise>
                    <dt> <b>Cofactors:</b></dt>
                </c:otherwise>
            </c:choose>
            <dd>
                <c:forEach var="cofactor" items="${proteinStructure.cofactors}">
                    <div class="small-molecule-container">
                        <fieldset class="epBox">
                            <a href="http://www.ebi.ac.uk/pdbe/entry/pdb/${proteinStructure.id}/bound/${cofactor}" target="blank">${cofactor}</a>
                            <div>
                                <div style="width: 200px;">
                                    <a style="border-bottom-style: none" target="blank" href="http://www.ebi.ac.uk/pdbe/entry/pdb/${proteinStructure.id}/bound/${cofactor}">
                                        <img src="//www.ebi.ac.uk/pdbe/static/chem-files/${cofactor}-100.gif" alt="${cofactor}">
                                    </a>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </c:forEach>

            </dd>
        </dl>

    </c:if>
    <c:if test="${not empty proteinStructure.ligands}">
        <dl>
            <c:choose>
                <c:when test="${fn:length(proteinStructure.ligands) == 1}">
                    <dt> <b>Ligand:</b></dt>
                </c:when>
                <c:otherwise>
                    <dt> <b>${fn:length(proteinStructure.ligands)} bound Ligands:</b></dt>
                </c:otherwise>
            </c:choose>

            <dd>
                <c:forEach var="ligand" items="${proteinStructure.ligands}">
                    <div class="small-molecule-container">

                        <fieldset class="epBox">
                            <a href="http://www.ebi.ac.uk/pdbe/entry/pdb/${proteinStructure.id}/bound/${ligand}" target="blank">${ligand}</a>
                            <div>
                                <div style="width: 200px;">
                                    <a style="border-bottom-style: none" target="blank" href="http://www.ebi.ac.uk/pdbe/entry/pdb/${proteinStructure.id}/bound/${ligand}">
                                        <img src="//www.ebi.ac.uk/pdbe/static/chem-files/${ligand}-100.gif" alt="${ligand}">
                                    </a>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </c:forEach>

            </dd>
        </dl>
    </c:if>
</div>
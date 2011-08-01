<html>
    <head>
        <title>Wellmia Top Health Interest Categories</title>
        <meta name="layout" content="wellmia" />
    </head>
    <body>
        <h1>TEMP TEXT:  There are lots of categories to choose from</h1>
        <h2>Here are the most followed health categories</h2>
        <ol>
            <g:each var="category" in="${topCategories}">
                <li><a class="news_teaser_dep link" href="/topics/${category.categoryTag.replace(' ','_')}">${category.categoryTag}</a> <!-- Movement:  ${category.lastInterestRank - category.interestRank} --></li>
          </g:each>
      </ol>
    </body>
</html>

Table = cell2table(cell(0,3));
Table.Properties.VariableNames = {'n' 'iterazioni', 'autovalore'};

tol = 10^(-5);

for n = 100:100:1000
   [l, i] = potenze(matrice_sparsa(n), tol, ones(n,1));

   record = {n, i, l};
   Table = [Table; record];
end

uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);

function [lambda, i] = potenze(A, tol, x0, maxit)
% function [lambda, i] = potenze(A, tol, [x0, maxit])
% Restituisce l'autovalore dominante della matrice A e il numero di 
% iterazioni necessarie per calcolarlo
% A matrice utilizzata per il calcolo
% tol tolleranza dell' approssimazione
% [x0] vettore di partenza
% [maxit] numero massimo di iterazioni
% lambda matrice quadrata nxn sparsa
% i numero di iterazioni
    [m,n] = size(A);
    if m ~= n 
        error('La matrice deve essere quadrata.'); 
    end
    if x0(:)==0
        error('Il vettore x0 non puo avere esclusivamente elementi nulli.');
    end % if da eliminare per calcolo con matrice
    
    if nargin <= 2
        x = rand(n, 1); 
    else
        x = x0;
    end
    if nargin <= 3
        maxit = 100*2*round(-log(tol));
    end
    x = x0; % da eliminare per calcolo con matrice
    x = x / norm(x);
    lambda = inf;
    for i=1:maxit
        lambda0 = lambda;
        v = A * x;
        lambda = x' * v;
        err = abs(lambda - lambda0);
        if err <= tol
            break
        end
        x = v/norm(v);
    end
    if err > tol
        warning("Raggiunto maxit");
    end
    return
end
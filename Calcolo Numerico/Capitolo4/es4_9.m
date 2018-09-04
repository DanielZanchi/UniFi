f = @(x) 1./(1 + x.^2);
a = -6;
b = 6;

Table = cell2table(cell(0,2));
Table.Properties.VariableNames = {'n' 'lebesgue'};

n = 2;
while n <= 40

    xi = linspace(a, b, n + 1); % ascisse equispaziate
    fi = f(xi); % valutazioni fun di runge

    p = @(x) lagrange(xi, fi, x); % lagrange
    e = @(x) abs(f(x) - p(x)); % errore di interpolazione

    % grafici
    figure(1)
    fplot(f, [a, b], 'g')
    hold on
    fplot(p, [a, b], 'r--')
    plot(xi, fi, 'ro')
    legend('Funzione di Runge','Approssimazione della funzione', 'Ascisse di interpolazione')
    hold off
    print('-dpng', strcat('graficiEs9/es4_9_img', num2str(n), '.png'));

    % grafico errore
    figure(2)
    x = linspace(5, b, 100001); % estremo destro
    plot(x, e(x), 'DisplayName', strcat('n = ', num2str(n)))
    hold on

    % tabella lebesgue
    x = linspace(a, b, 100001);
    leb = zeros(length(x), 1)';
    for k = 1:n+1
        leb = leb + abs(l_k_n(x, xi, k));
    end
    leb = norm(leb, inf);

    record = {n, leb};
    Table = [Table; record];

    n = n + 2;
end

% mostra la tabella
figure(3)
uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);


function y = lagrange(xi, fi, x)
% function y = lagrange( xi, fi, x )
% xi vettore dei punti di ascissa
% fi vettore dei valori di f(x)
% x vettore di punti in cui calcolare f(x)
% y valore di f(x)
if length(xi) ~= length(fi)
    error('xi e fi hanno lunghezza diversa, deve essere uguale')
end
n = length(xi)-1;
m = length(x);
y = zeros(size(x));
for i=1:m
    for j=1:n+1
        p = 1;
        for k=1:n+1
            if j~=k
                p = p*(x(i)-xi(k))/(xi(j)-xi(k));
            end
        end
        y(i) = y(i)+fi(j)*p;
    end
end
return
end

function lkn = l_k_n(x, xi, k)
    lkn = zeros(length(x), 1)' + 1;
    for i = 1:length(xi)
        if i ~= k
            pterm = (x - xi(i)) / (xi(k) - xi(i));
            lkn = prod([lkn; pterm], 1);
        end
    end
end
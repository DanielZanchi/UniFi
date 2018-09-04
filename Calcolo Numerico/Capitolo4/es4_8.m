syms x
runge = 1/(1+x^2);
a = -6;
b = 6;

Table = cell2table(cell(0,2));
Table.Properties.VariableNames = {'n' 'lebesgue'};

n = 2;
while n<=40
    iteration = {n, (2/pi)*(log(n))};
    Table = [Table; iteration];
    n = n + 2;
end

uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);
